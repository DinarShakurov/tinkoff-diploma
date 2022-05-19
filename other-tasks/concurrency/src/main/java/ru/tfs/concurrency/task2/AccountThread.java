package ru.tfs.concurrency.task2;

public class AccountThread implements Runnable {

    private final Account accountFrom;
    private final Account accountTo;
    private final int money;


    public AccountThread(Account accountFrom, Account accountTo, int money) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
    }

    @Override
    public void run() {
        int hashFrom = System.identityHashCode(accountFrom);
        int hashTo = System.identityHashCode(accountTo);
        for (int i = 0; i < 4000; i++) {
            if (hashFrom > hashTo) {
                synchronized (accountTo) {
                    synchronized (accountFrom) {
                        doTransfer();
                    }
                }
            } else if (hashFrom < hashTo) {
                synchronized (accountFrom) {
                    synchronized (accountTo) {
                        doTransfer();
                    }
                }
            } else {
                while (true) {
                    boolean lockFirst = accountFrom.getLock().tryLock();
                    boolean lockSecond = accountTo.getLock().tryLock();
                    if (lockFirst && lockSecond) {
                        doTransfer();
                        accountFrom.getLock().unlock();
                        accountTo.getLock().unlock();
                        break;
                    } else {
                        if (lockFirst) {
                            accountFrom.getLock().unlock();
                        }
                        if (lockSecond) {
                            accountTo.getLock().unlock();
                        }
                    }
                }
            }
        }
    }

    private void doTransfer() {
        System.out.printf("making transfer from %s (balance = %d) to %s (balance = %d)\n",
                accountFrom.toString(), accountFrom.getCacheBalance(),
                accountTo.toString(), accountTo.getCacheBalance());
        if (accountFrom.takeOffMoney(money)) {
            accountTo.addMoney(money);
        }
    }
}