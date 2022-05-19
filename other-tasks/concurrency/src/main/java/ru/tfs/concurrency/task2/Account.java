package ru.tfs.concurrency.task2;

import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final ReentrantLock lock = new ReentrantLock(true);
    private int cacheBalance;
    private String name;

    public Account(int cacheBalance, String name) {
        this.cacheBalance = cacheBalance;
        this.name = name;
    }

    public void addMoney(int money) {
        this.cacheBalance += money;
    }

    public boolean takeOffMoney(int money) {
        if (this.cacheBalance < money) {
            return false;
        }

        this.cacheBalance -= money;
        return true;
    }

    public int getCacheBalance() {
        return cacheBalance;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return name;
    }
}