package com.greatfree.cluster.tpc.data;

public class Account {
    private final String accountNumber;
    private final String bankId;
    private double balance;
    
    public Account(String accountNumber, String bankId, double initialBalance) {
        this.accountNumber = accountNumber;
        this.bankId = bankId;
        this.balance = initialBalance;
    }
    
    public String getAccountNumber() { return accountNumber; }
    public String getBankId() { return bankId; }
    public double getBalance() { return balance; }
    
    public void debit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Debit amount must be positive");
        if (balance < amount) throw new InsufficientFundsException("Insufficient funds");
        this.balance -= amount;
    }
    
    public void credit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Credit amount must be positive");
        this.balance += amount;
    }
    
    @Override
    public String toString() {
        return String.format("Account[%s-%s: $%.2f]", bankId, accountNumber, balance);
    }
}

// Custom exception
class InsufficientFundsException extends RuntimeException {
    private static final long serialVersionUID = 6587696985808842348L;

	public InsufficientFundsException(String message) { super(message); }
}

