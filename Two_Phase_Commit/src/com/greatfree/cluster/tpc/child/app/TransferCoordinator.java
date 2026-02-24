package com.greatfree.cluster.tpc.child.app;




public class TransferCoordinator {
    private final String transactionId;
    private final BankParticipant sourceBank;
    private final BankParticipant destBank;
    private final String sourceAccount;
    private final String destAccount;
    private final double amount;
    
    public TransferCoordinator(String transactionId, 
                              BankParticipant sourceBank, String sourceAccount,
                              BankParticipant destBank, String destAccount,
                              double amount) {
        this.transactionId = transactionId;
        this.sourceBank = sourceBank;
        this.destBank = destBank;
        this.sourceAccount = sourceAccount;
        this.destAccount = destAccount;
        this.amount = amount;
    }
    
    public boolean executeTransfer() {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("TRANSACTION %s: Transfer $%.2f from Bank %s Account %s to Bank %s Account %s%n",
            transactionId, amount, sourceBank.getBankId(), sourceAccount, 
            destBank.getBankId(), destAccount);
        System.out.println("=".repeat(80));
        
        // Show initial state
        System.out.println("\nInitial Account States:");
        sourceBank.displayAccounts();
        destBank.displayAccounts();
        
        // Phase 1: Prepare
        System.out.println("\n--- PHASE 1: PREPARE ---");
        boolean sourcePrepared = sourceBank.prepare(transactionId, sourceAccount, amount, true);
        boolean destPrepared = destBank.prepare(transactionId, destAccount, amount, false);
        
        // Phase 2: Decision
        System.out.println("\n--- PHASE 2: DECISION ---");
        if (sourcePrepared && destPrepared) {
            System.out.println("✓ All banks prepared successfully. Decision: COMMIT");
            sourceBank.commit(transactionId);
            destBank.commit(transactionId);
            System.out.println("\n✓ TRANSACTION COMMITTED SUCCESSFULLY");
            return true;
        } else {
            System.out.println("✗ Prepare failed. Decision: ABORT");
            if (sourcePrepared) sourceBank.abort(transactionId);
            if (destPrepared) destBank.abort(transactionId);
            System.out.println("\n✗ TRANSACTION ABORTED");
            return false;
        }
    }
    
    public void showFinalState() {
        System.out.println("\nFinal Account States:");
        sourceBank.displayAccounts();
        destBank.displayAccounts();
        System.out.println("=".repeat(80) + "\n");
    }
}
