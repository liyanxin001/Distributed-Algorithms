package com.greatfree.cluster.tpc.child.app;




/**
 * Coordinator orchestrates the Two-Phase Commit protocol.
 * It clearly shows the two phases and the decision logic.
 */
class Coordinator {
    private final String transactionId;
    private final Participant sourceBank;
    private final Participant destBank;
    private final String fromAccount;
    private final String toAccount;
    private final double amount;
    
    public Coordinator(String transactionId, 
                              Participant sourceBank, String fromAccount,
                              Participant destBank, String toAccount,
                              double amount) {
        this.transactionId = transactionId;
        this.sourceBank = sourceBank;
        this.destBank = destBank;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }
    
    /**
     * Executes the Two-Phase Commit protocol:
     * 
     * PHASE 1: Ask both banks to PREPARE
     * - If BOTH vote YES -> proceed to PHASE 2 COMMIT
     * - If ANY votes NO  -> proceed to PHASE 2 ABORT
     * 
     * PHASE 2: Tell both banks the decision
     * - COMMIT: Both banks permanently apply the changes
     * - ABORT: Both banks release locks, no changes applied
     */
    public boolean executeTransfer() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COORDINATOR: Starting transaction " + transactionId);
        System.out.printf("Transfer $%.2f from %s (Bank A) to %s (Bank B)%n", 
            amount, fromAccount, toAccount);
        System.out.println("=".repeat(80));
        
        // Show balances before transaction
        System.out.println("\nBALANCES BEFORE TRANSACTION:");
        sourceBank.displayAccounts();
        destBank.displayAccounts();
        
        // ========== PHASE 1: PREPARE ==========
        System.out.println("\n" + "-".repeat(40));
        System.out.println("PHASE 1: PREPARE");
        System.out.println("-".repeat(40));
        
        System.out.println("Coordinator asks: Can you prepare?");
        boolean sourceVote = sourceBank.prepare(transactionId, fromAccount, amount, true);
        boolean destVote = destBank.prepare(transactionId, toAccount, amount, false);
        
        System.out.println("\nVOTE RESULTS:");
        System.out.println("  Source Bank (withdraw from " + fromAccount + "): " + 
            (sourceVote ? "YES" : "NO"));
        System.out.println("  Dest Bank (deposit to " + toAccount + "): " + 
            (destVote ? "YES" : "NO"));
        
        // ========== PHASE 2: DECISION ==========
        System.out.println("\n" + "-".repeat(40));
        System.out.println("PHASE 2: DECISION");
        System.out.println("-".repeat(40));
        
        boolean allVotedYes = sourceVote && destVote;
        
        if (allVotedYes) {
            System.out.println("✓ ALL VOTES YES. Coordinator decides: COMMIT");
            sourceBank.commit(transactionId);
            destBank.commit(transactionId);
            System.out.println("\n✓ TRANSACTION COMMITTED SUCCESSFULLY");
        } else {
            System.out.println("✗ SOME VOTES NO. Coordinator decides: ABORT");
            if (sourceVote) sourceBank.abort(transactionId);
            if (destVote) destBank.abort(transactionId);
            System.out.println("\n✗ TRANSACTION ABORTED - No money moved");
        }
        
        // Show balances after transaction
        System.out.println("\nBALANCES AFTER TRANSACTION:");
        sourceBank.displayAccounts();
        destBank.displayAccounts();
        System.out.println("=".repeat(80) + "\n");
        
        return allVotedYes;
    }
}