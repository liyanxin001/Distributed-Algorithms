package com.greatfree.cluster.tpc.data;

public class BankAccount {
	
	private String userName;
	private double balance;
	
    public BankAccount(String userName, double balance) {
    	this.setUserName(userName);
    	this.setBalance(balance);
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
