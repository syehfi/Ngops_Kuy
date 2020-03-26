package com.uts.syehfi.ngopskuy.models;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int balance;
    private List<Transaction> transactions;

    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        balance += transaction.getAmount();
        this.transactions.add(transaction);
    }

    public void removeTransaction(int index) {
        Transaction transaction = transactions.get(index);
        balance -= transaction.getAmount();
        this.transactions.remove(transaction);
    }

    public void updateTransaction(int index, Transaction transaction) {
        this.transactions.set(index, transaction);
    }
}
