package ru.tfs.concurrency.task1;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.allOf;

public class Leg3 implements Runnable {

    private final String name;
    private final Lock lock1;
    private final Lock lock2;


    public Leg3(String name, Lock lock1, Lock lock2) {
        this.name = name;
        this.lock1 = lock1;
        this.lock2 = lock2;
    }


    @Override
    public void run() {
        try {
            while (true) {
                lock1.lock();
                lock2.lock();
                System.out.println(name);
                lock1.unlock();
                Thread.sleep(1);
                lock2.unlock();
            }
        } catch (Exception ignored) {
        } finally {
            lock1.unlock();
            lock2.unlock();
        }
    }

    public static void main(String[] args) {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        allOf(
                runAsync(new Leg3("left", lock1, lock2)),
                runAsync(new Leg3("right", lock1, lock2))
        ).join();
    }
}