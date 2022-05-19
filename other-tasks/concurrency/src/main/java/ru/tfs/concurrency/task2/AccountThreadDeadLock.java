package ru.tfs.concurrency.task2;

public class AccountThreadDeadLock implements Runnable {
    private static Object LOCK1 = new Object();

    private final Account accountFrom;
    private final Account accountTo;
    private final int money;

    public AccountThreadDeadLock(Account accountFrom, Account accountTo, int money) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4000; i++) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    System.out.println(accountFrom + " " + accountTo);
                    if (accountFrom.takeOffMoney(money)) {
                        accountTo.addMoney(money);
                    }
                }
            }
        }
    }
}