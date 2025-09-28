package com.example.horseracing.data;

public class GameState {
    private static GameState instance;
    private int balance = -1;

    public static synchronized GameState getInstance() {
        if (instance == null) instance = new GameState();
        return instance;
    }

    public void ensureInitialBalance() {
        if (balance < 0) balance = 100; // default mock 100$
    }

    public int getBalance() {
        ensureInitialBalance();
        return balance;
    }

    public void addBalance(int delta) {
        ensureInitialBalance();
        balance += delta;
        if (balance < 0) balance = 0;
    }
}


