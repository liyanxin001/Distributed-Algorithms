package com.greatfree.cluster.tpc.data;



public class Account {
    private final String accountNumber;
    private double balance;
    
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    
    public void withdraw(double amount) {
        if (balance < amount) {
            throw new InsufficientFundsException(
                String.format("Insufficient funds: have $%.2f, need $%.2f", balance, amount));
        }
        balance -= amount;
    }
    
    public void deposit(double amount) {
        balance += amount;
    }
    
    @Override
    public String toString() {
        return String.format("%s: $%.2f", accountNumber, balance);
    }
}

class InsufficientFundsException extends RuntimeException {
    private static final long serialVersionUID = 2363070223116303575L;

	public InsufficientFundsException(String message) { super(message); }
}
