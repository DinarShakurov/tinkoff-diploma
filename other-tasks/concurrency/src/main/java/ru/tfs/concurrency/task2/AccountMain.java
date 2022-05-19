package ru.tfs.concurrency.task2;

import java.util.concurrent.CompletableFuture;

public class AccountMain {

    public static void main(String[] args) {
        withoutDeadlock();
    }

    public static void withoutDeadlock() {
        Account firstAccount = new Account(100_000, "Петя");
        Account secondAccount = new Account(100_000, "Вася");

        AccountThread firstThread = new AccountThread(firstAccount, secondAccount, 100);
        AccountThread secondThread = new AccountThread(secondAccount, firstAccount, 100);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(firstThread),
                CompletableFuture.runAsync(secondThread)
        ).join();

        System.out.println("Cash balance of the first account: " + firstAccount.getCacheBalance());
        System.out.println("Cash balance of the second account: " + secondAccount.getCacheBalance());
    }

    public static void withDeadlock() {
        Account firstAccount = new Account(100_000, "Петя");
        Account secondAccount = new Account(100_000, "Вася");

        AccountThreadDeadLock firstThread = new AccountThreadDeadLock(firstAccount, secondAccount, 100);
        AccountThreadDeadLock secondThread = new AccountThreadDeadLock(secondAccount, firstAccount, 100);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(firstThread),
                CompletableFuture.runAsync(secondThread)
        ).join();

        System.out.println("Cash balance of the first account: " + firstAccount.getCacheBalance());
        System.out.println("Cash balance of the second account: " + secondAccount.getCacheBalance());
    }
}