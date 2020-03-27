package com.uts.syehfi.ngopskuy.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int balance;
    private List<Transaction> transactions;

    public Order() {
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
        balance += transaction.getJmlHarga();
        this.transactions.add(transaction);
    }

    public void removeTransaction(int index) {
        Transaction transaction = transactions.get(index);
        balance -= transaction.getJmlHarga();
        this.transactions.remove(transaction);
    }

    public void updateTransaction(int index, Transaction transaction) {
        this.transactions.set(index, transaction);
        this.balance = 0;
        for (Transaction t : this.transactions) {
            if (t.getType() == Transaction.Type.STRONG) {
                balance += t.getJmlHarga();
            } else {
                balance += t.getJmlHarga();
            }
        }
    }
}
