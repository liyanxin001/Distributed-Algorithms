package com.greatfree.cluster.tpc.child.app;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.greatfree.cluster.tpc.data.Account;

/**
 * Represents the possible states of a distributed transaction within a participant.
 * This follows the standard 2PC state transition:
 * ACTIVE -> PREPARED -> COMMITTED
 * ACTIVE -> PREPARED -> ABORTED
 */
enum TransactionState {
    ACTIVE,      // Transaction just started, no locks yet
    PREPARED,    // Transaction has voted YES and holds locks
    COMMITTED,   // Transaction has been successfully applied
    ABORTED      // Transaction was rolled back
}

/**
 * Simple bank account entity.
 */


/**
 * Represents a lock held on an account by a transaction.
 * In 2PC, locks are acquired during PREPARE phase and released
 * during COMMIT or ABORT phase.
 */
class AccountLock {
    final String transactionId;   // Which transaction holds this lock
    final String accountNumber;   // Which account is locked
    final long lockAcquiredTime;  // For timeout detection
    
    AccountLock(String transactionId, String accountNumber) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.lockAcquiredTime = System.currentTimeMillis();
    }
}

/**
 * Represents a transaction that is pending (in PREPARED state).
 * The actual account modification has NOT happened yet - 
 * it will only happen if COMMIT is received.
 */
class PendingTransaction {
    final String accountNumber;   // Account to be modified
    final double amount;          // Amount to transfer
    final OperationType operation; // WITHDRAW or DEPOSIT
    TransactionState state;       // Current state (should be PREPARED)
    
    enum OperationType {
        WITHDRAW, DEPOSIT
    }
    
    PendingTransaction(String accountNumber, double amount, OperationType operation) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.operation = operation;
        this.state = TransactionState.ACTIVE;
    }
    
    boolean isWithdraw() { return operation == OperationType.WITHDRAW; }
    boolean isDeposit() { return operation == OperationType.DEPOSIT; }
}

/**
 * BankParticipant represents a single database node that participates
 * in distributed transactions using the Two-Phase Commit protocol.
 * 
 * CRITICAL BEHAVIOR:
 * - PREPARE: Validates and locks, but DOES NOT modify account balances
 * - COMMIT: Actually modifies account balances (finally!)
 * - ABORT: Releases locks, discards pending work
 */
public class Participant {
    
    // Core data structures
    private final Map<String, Account> accounts;                    // accountNumber -> Account
    private final Map<String, PendingTransaction> pendingTransactions; // transactionId -> PendingTransaction
    private final Map<String, AccountLock> accountLocks;            // accountNumber -> AccountLock
    
    private final String participantId;  // For identification (e.g., "Database-A")
    
    public Participant(String participantId) {
        this.participantId = participantId;
        this.accounts = new ConcurrentHashMap<>();
        this.pendingTransactions = new ConcurrentHashMap<>();
        this.accountLocks = new ConcurrentHashMap<>();
    }
    
    // ==================== Account Management ====================
    
    public void addAccount(String accountNumber, double initialBalance) {
        accounts.put(accountNumber, new Account(accountNumber, initialBalance));
    }
    
    public double getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : -1;
    }
    
    public boolean isAccountLocked(String accountNumber) {
        return accountLocks.containsKey(accountNumber);
    }
    
    public String getLockingTransaction(String accountNumber) {
        AccountLock lock = accountLocks.get(accountNumber);
        return lock != null ? lock.transactionId : null;
    }
    
    // ==================== TWO-PHASE COMMIT METHODS ====================
    
    /**
     * PHASE 1: PREPARE
     * 
     * This method votes on whether the transaction can commit.
     * 
     * What happens during PREPARE:
     * 1. Validate account exists
     * 2. Check if account is already locked by ANOTHER transaction
     * 3. For withdrawals, verify sufficient funds
     * 4. If all checks pass: ACQUIRE LOCK and record pending transaction
     * 5. Return true = VOTE YES, false = VOTE NO
     * 
     * IMPORTANT: Account balance is NOT modified during PREPARE.
     * The actual withdrawal/deposit happens only during COMMIT.
     * 
     * @param transactionId Unique ID for this distributed transaction
     * @param accountNumber Which account to operate on
     * @param amount How much money
     * @param isWithdraw true = money leaves account, false = money enters account
     * @return true = VOTE YES (ready to commit), false = VOTE NO (cannot commit)
     */
    public boolean prepare(String transactionId, String accountNumber, 
                          double amount, boolean isWithdraw) {
        
        String operation = isWithdraw ? "WITHDRAW" : "DEPOSIT";
        log("PREPARE phase: Transaction %s wants to %s $%.2f from/to %s", 
            transactionId, operation, amount, accountNumber);
        
        // ----- VALIDATION STEP 1: Does the account exist? -----
        Account account = accounts.get(accountNumber);
        if (account == null) {
            log("  ❌ REJECTED: Account %s does not exist. Voting NO.", accountNumber);
            return false;  // VOTE NO
        }
        
        // ----- VALIDATION STEP 2: Is the account locked by another transaction? -----
        AccountLock existingLock = accountLocks.get(accountNumber);
        if (existingLock != null && !existingLock.transactionId.equals(transactionId)) {
            log("  ❌ REJECTED: Account %s is locked by transaction %s. Voting NO.", 
                accountNumber, existingLock.transactionId);
            return false;  // VOTE NO
        }
        
        // ----- VALIDATION STEP 3: For withdrawals, check sufficient funds -----
        if (isWithdraw && account.getBalance() < amount) {
            log("  ❌ REJECTED: Insufficient funds in %s. Balance: $%.2f, Need: $%.2f. Voting NO.",
                accountNumber, account.getBalance(), amount);
            return false;  // VOTE NO
        }
        
        // ----- ALL CHECKS PASSED: Acquire lock and record pending transaction -----
        
        // Acquire lock if not already locked by this transaction
        if (existingLock == null) {
            accountLocks.put(accountNumber, new AccountLock(transactionId, accountNumber));
            log("  🔒 Lock acquired on %s", accountNumber);
        } else {
            log("  🔒 Account already locked by this transaction (idempotent prepare)");
        }
        
        // Record the pending operation (remember: we haven't done it yet!)
        PendingTransaction.OperationType op = 
            isWithdraw ? PendingTransaction.OperationType.WITHDRAW 
                       : PendingTransaction.OperationType.DEPOSIT;
        
        PendingTransaction pending = new PendingTransaction(accountNumber, amount, op);
        pending.state = TransactionState.PREPARED;
        pendingTransactions.put(transactionId, pending);
        
        log("  ✅ PREPARE successful. VOTE YES. (Account balance unchanged: $%.2f)", 
            account.getBalance());
        return true;  // VOTE YES
    }
    
    /**
     * PHASE 2: COMMIT
     * 
     * This method FINALLY applies the changes to the account.
     * Called by coordinator AFTER all participants voted YES.
     * 
     * What happens during COMMIT:
     * 1. Verify this transaction was prepared
     * 2. Apply the actual withdrawal or deposit (finally!)
     * 3. Release the lock
     * 4. Remove the pending transaction record
     * 
     * @param transactionId The transaction to commit
     */
    public void commit(String transactionId) {
        log("COMMIT phase: Transaction %s", transactionId);
        
        // ----- STEP 1: Find the pending transaction -----
        PendingTransaction pending = pendingTransactions.get(transactionId);
        if (pending == null) {
            log("  ❌ ERROR: No prepared transaction found for %s. Cannot commit.", transactionId);
            return;
        }
        
        if (pending.state != TransactionState.PREPARED) {
            log("  ❌ ERROR: Transaction %s is in state %s, expected PREPARED. Cannot commit.",
                transactionId, pending.state);
            return;
        }
        
        // ----- STEP 2: Apply the actual operation (THIS IS WHERE MONEY MOVES) -----
        Account account = accounts.get(pending.accountNumber);
        if (account == null) {
            // This should never happen if prepare passed, but check anyway
            log("  ❌ CRITICAL ERROR: Account %s disappeared between prepare and commit!", 
                pending.accountNumber);
            // In real system, this would trigger recovery procedures
            pending.state = TransactionState.ABORTED;
            pendingTransactions.remove(transactionId);
            accountLocks.remove(pending.accountNumber);
            return;
        }
        
        double balanceBefore = account.getBalance();
        
        if (pending.isWithdraw()) {
            account.withdraw(pending.amount);
            log("  💸 Withdrew $%.2f from %s", pending.amount, pending.accountNumber);
        } else { // DEPOSIT
            account.deposit(pending.amount);
            log("  💰 Deposited $%.2f to %s", pending.amount, pending.accountNumber);
        }
        
        double balanceAfter = account.getBalance();
        log("     Balance changed: $%.2f -> $%.2f", balanceBefore, balanceAfter);
        
        // ----- STEP 3: Release the lock -----
        accountLocks.remove(pending.accountNumber);
        log("  🔓 Lock released on %s", pending.accountNumber);
        
        // ----- STEP 4: Clean up pending transaction record -----
        pending.state = TransactionState.COMMITTED;
        pendingTransactions.remove(transactionId);
        
        log("  ✅ Transaction %s COMMITTED successfully", transactionId);
    }
    
    /**
     * PHASE 2: ABORT
     * 
     * This method cancels the transaction without applying changes.
     * Called by coordinator if ANY participant voted NO.
     * 
     * What happens during ABORT:
     * 1. Release any locks held for this transaction
     * 2. Discard the pending transaction (no changes to account!)
     * 
     * @param transactionId The transaction to abort
     */
    public void abort(String transactionId) {
        log("ABORT phase: Transaction %s", transactionId);
        
        // ----- STEP 1: Find the pending transaction -----
        PendingTransaction pending = pendingTransactions.get(transactionId);
        if (pending == null) {
            log("  No pending transaction found for %s (already aborted or never prepared)", 
                transactionId);
            return;
        }
        
        // ----- STEP 2: Release lock if held -----
        AccountLock lock = accountLocks.get(pending.accountNumber);
        if (lock != null && lock.transactionId.equals(transactionId)) {
            accountLocks.remove(pending.accountNumber);
            log("  🔓 Lock released on %s", pending.accountNumber);
        }
        
        // ----- STEP 3: Discard pending transaction -----
        double balance = accounts.get(pending.accountNumber).getBalance();
        pending.state = TransactionState.ABORTED;
        pendingTransactions.remove(transactionId);
        
        log("  ✅ Transaction %s ABORTED. Account %s balance unchanged: $%.2f", 
            transactionId, pending.accountNumber, balance);
    }
    
    // ==================== Utility Methods ====================
    
    public void displayAccounts() {
        System.out.printf("\n[%s] Current Account Balances:%n", participantId);
        if (accounts.isEmpty()) {
            System.out.println("  No accounts");
        } else {
            accounts.values().forEach(acc -> {
                String lockStatus = accountLocks.containsKey(acc.getAccountNumber()) 
                    ? " (LOCKED by " + accountLocks.get(acc.getAccountNumber()).transactionId + ")" 
                    : "";
                System.out.printf("  %s%s%n", acc, lockStatus);
            });
        }
    }
    
    private void log(String format, Object... args) {
        System.out.printf("[%s] " + format + "%n", participantId, args);
    }
    
    /**
     * For debugging: shows current locks held
     */
    public void displayLocks() {
        if (accountLocks.isEmpty()) {
            System.out.printf("[%s] No locks held%n", participantId);
        } else {
            System.out.printf("[%s] Current locks:%n", participantId);
            accountLocks.values().forEach(lock -> 
                System.out.printf("  %s locked by %s%n", 
                    lock.accountNumber, lock.transactionId));
        }
    }
}