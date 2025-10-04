package com.example.horseracing.data;

public class DepositHistoryItem {
    private int amount;
    private long timestamp;

    public DepositHistoryItem(int amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

