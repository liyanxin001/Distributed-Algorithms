package com.greatfree.cluster.tpc.child.app;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



import com.greatfree.cluster.tpc.data.Account;


enum TransactionState {
    ACTIVE, PREPARED, COMMITTED, ABORTED, IN_DOUBT
}


public class BankParticipant {
    private final String bankId;
    private final Map<String, Account> accounts;
    private final Map<String, PendingTransaction> pendingTransactions;
    private final Map<String, Lock> accountLocks;
    
    // Inner class to track locks
    private static class Lock {
        final String transactionId;
        Lock(String transactionId, String accountNumber, boolean isExclusive) {
            this.transactionId = transactionId;
        }
    }
    
    // Inner class for pending transaction state
    private static class PendingTransaction {
        final String accountNumber;
        final double amount;
        final boolean isDebit; // true for debt, false for credit
        TransactionState state;
        
        PendingTransaction(String transactionId, String accountNumber, 
                          double amount, boolean isDebit) {
            this.accountNumber = accountNumber;
            this.amount = amount;
            this.isDebit = isDebit;
            this.state = TransactionState.ACTIVE;
        }
    }
    
    public BankParticipant(String bankId) {
        this.bankId = bankId;
        this.accounts = new ConcurrentHashMap<>();
        this.pendingTransactions = new ConcurrentHashMap<>();
        this.accountLocks = new ConcurrentHashMap<>();
        initializeAccounts();
    }
    
    private void initializeAccounts() {
        // Create some sample accounts
        accounts.put("1001", new Account("1001", bankId, 5000.0));
        accounts.put("1002", new Account("1002", bankId, 3000.0));
        accounts.put("1003", new Account("1003", bankId, 10000.0));
    }
    
    public String getBankId() { return bankId; }
    
    // Phase 1: Prepare
    public boolean prepare(String transactionId, String accountNumber, 
                          double amount, boolean isDebit) {
        System.out.printf("[Bank %s] [TX: %s] Prepare request: %s $%.2f from account %s%n",
            bankId, transactionId, isDebit ? "DEBIT" : "CREDIT", amount, accountNumber);
        
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.printf("[Bank %s] [TX: %s] Account %s not found. Voting NO%n",
                bankId, transactionId, accountNumber);
            return false;
        }
        
        // Check if account is already locked by another transaction
        Lock existingLock = accountLocks.get(accountNumber);
        if (existingLock != null && !existingLock.transactionId.equals(transactionId)) {
            System.out.printf("[Bank %s] [TX: %s] Account %s locked by TX: %s. Voting NO%n",
                bankId, transactionId, accountNumber, existingLock.transactionId);
            return false;
        }
        
        // For debit operations, verify sufficient funds
        if (isDebit && account.getBalance() < amount) {
            System.out.printf("[Bank %s] [TX: %s] Insufficient funds. Balance: $%.2f, Required: $%.2f. Voting NO%n",
                bankId, transactionId, account.getBalance(), amount);
            return false;
        }
        
        // Acquire lock on the account
        accountLocks.put(accountNumber, new Lock(transactionId, accountNumber, true));
        
        // Record pending transaction
        PendingTransaction pending = new PendingTransaction(
            transactionId, accountNumber, amount, isDebit);
        pending.state = TransactionState.PREPARED;
        pendingTransactions.put(transactionId, pending);
        
        System.out.printf("[Bank %s] [TX: %s] Prepared. Locks acquired. Voting YES%n",
            bankId, transactionId);
        return true;
    }
    
    // Phase 2: Commit
    public void commit(String transactionId) {
        System.out.printf("[Bank %s] [TX: %s] Commit request received%n", bankId, transactionId);
        
        PendingTransaction pending = pendingTransactions.get(transactionId);
        if (pending == null) {
            System.out.printf("[Bank %s] [TX: %s] No pending transaction found%n", 
                bankId, transactionId);
            return;
        }
        
        if (pending.state != TransactionState.PREPARED) {
            System.out.printf("[Bank %s] [TX: %s] Cannot commit: invalid state %s%n",
                bankId, transactionId, pending.state);
            return;
        }
        
        try {
            Account account = accounts.get(pending.accountNumber);
            
            // Apply the actual operation
            if (pending.isDebit) {
                account.debit(pending.amount);
                System.out.printf("[Bank %s] [TX: %s] Debited $%.2f from account %s. New balance: $%.2f%n",
                    bankId, transactionId, pending.amount, pending.accountNumber, account.getBalance());
            } else {
                account.credit(pending.amount);
                System.out.printf("[Bank %s] [TX: %s] Credited $%.2f to account %s. New balance: $%.2f%n",
                    bankId, transactionId, pending.amount, pending.accountNumber, account.getBalance());
            }
            
            // Update state
            pending.state = TransactionState.COMMITTED;
            
            // Release lock
            accountLocks.remove(pending.accountNumber);
            
            // Remove from pending after successful commit
            pendingTransactions.remove(transactionId);
            
        } catch (Exception e) {
            System.out.printf("[Bank %s] [TX: %s] Commit failed: %s%n",
                bankId, transactionId, e.getMessage());
            // In real system, this would trigger recovery
        }
    }
    
    // Phase 2: Abort
    public void abort(String transactionId) {
        System.out.printf("[Bank %s] [TX: %s] Abort request received%n", bankId, transactionId);
        
        PendingTransaction pending = pendingTransactions.get(transactionId);
        if (pending == null) {
            System.out.printf("[Bank %s] [TX: %s] No pending transaction found%n", 
                bankId, transactionId);
            return;
        }
        
        // Release lock
        accountLocks.remove(pending.accountNumber);
        
        // Mark as aborted
        pending.state = TransactionState.ABORTED;
        pendingTransactions.remove(transactionId);
        
        System.out.printf("[Bank %s] [TX: %s] Aborted. Locks released.%n", 
            bankId, transactionId);
    }
    
    // Query account balance
    public double getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : -1;
    }
    
    // Display all accounts
    public void displayAccounts() {
        System.out.printf("\n=== Bank %s Accounts ===%n", bankId);
        accounts.values().forEach(account -> 
            System.out.printf("  %s%n", account));
    }
}

