package ru.tfs.concurrency.task3;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Workers {

    public static void pool() throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool(5);

        List<Callable<Object>> orderToMachine = IntStream.rangeClosed(1, 8)
                .mapToObj(i -> Executors.callable(() -> workOnMachine(i)))
                .toList();

        executorService.invokeAll(orderToMachine);
        executorService.shutdown();
    }

    public static void synchronizer() {
        Semaphore semaphore = new Semaphore(5);

        IntStream.rangeClosed(1, 8)
                .mapToObj(id -> new Thread(
                        () -> {
                            try {
                                semaphore.acquire();
                                workOnMachine(id);
                                semaphore.release();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }))
                .toList()
                .forEach(Thread::start);
    }

    private static void workOnMachine(int workerId) {
        try {
            System.out.println("worker " + workerId + " occupy production machine ...");
            Thread.sleep(2000);
            System.out.println("worker " + workerId + " release production machine");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        pool();
        System.out.println();
        synchronizer();
    }
}
