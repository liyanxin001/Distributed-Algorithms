package com.greatfree.cluster.tpc.child.app;

import java.util.ArrayList;
import java.util.List;

class TransactionCoordinator {
    private final String transactionId;
    private final List<Participant> participants;

    public TransactionCoordinator(String transactionId) {
        this.transactionId = transactionId;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    // The main method executing the 2PC protocol
    public boolean executeDistributedTransaction() {
        System.out.println("\n=== Starting Two-Phase Commit for Transaction: " + transactionId + " ===");

        // --- Phase 1: Prepare ---
        System.out.println("--- Phase 1: PREPARE ---");
        List<Participant> preparedParticipants = new ArrayList<>();
        boolean allVotesYes = true;

        for (Participant p : participants) {
            boolean vote = p.prepare(transactionId);
            if (vote) {
                preparedParticipants.add(p); // Track who voted yes
            } else {
                allVotesYes = false;
                // We continue to collect votes even if one fails, but we will abort later.
            }
        }

        // --- Phase 2: Commit or Abort ---
        System.out.println("--- Phase 2: DECISION ---");
        if (allVotesYes) {
            System.out.println("Coordinator decision: COMMIT (all voted YES)");
            for (Participant p : participants) {
                p.commit(transactionId);
            }
            System.out.println("=== Transaction COMMITTED successfully. ===\n");
            return true;
        } else {
            System.out.println("Coordinator decision: ABORT (at least one voted NO or failed)");
            for (Participant p : participants) {
                p.abort(transactionId);
            }
            System.out.println("=== Transaction ABORTED. ===\n");
            return false;
        }
    }
}