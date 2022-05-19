package ru.tfs.concurrency.task1;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;

public class Leg1 implements Runnable {

    private final String name;
    private final AtomicBoolean atomicBoolean;
    private final Consumer<AtomicBoolean> consumer;
    private final Predicate<AtomicBoolean> predicate;

    public Leg1(String name,
                AtomicBoolean atomicBoolean,
                Consumer<AtomicBoolean> consumer,
                Predicate<AtomicBoolean> predicate) {
        this.name = name;
        this.atomicBoolean = atomicBoolean;
        this.consumer = consumer;
        this.predicate = predicate;
    }

    @Override
    public void run() {
        while (true) {
            if (predicate.test(atomicBoolean)) {
                System.out.println(name);
                consumer.accept(atomicBoolean);
            }
        }
    }

    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Predicate<AtomicBoolean> leftPredicate = atomicBoolean1 -> atomicBoolean1.get();
        Predicate<AtomicBoolean> rightPredicate = atomicBoolean1 -> !atomicBoolean1.get();

        Consumer<AtomicBoolean> leftConsumer = atomicBoolean1 -> atomicBoolean1.set(false);
        Consumer<AtomicBoolean> rightConsumer = atomicBoolean1 -> atomicBoolean1.set(true);

        allOf(
                runAsync(new Leg1("left", atomicBoolean, leftConsumer, leftPredicate)),
                runAsync(new Leg1("right", atomicBoolean, rightConsumer, rightPredicate))
        ).join();
    }
}