package ru.tfs.concurrency.task1;

import java.util.ArrayList;
import java.util.List;

public class Leg2 {

    static class Producer implements Runnable {
        private final List<Object> list;
        private final int MAX_SIZE = 1;

        public Producer(List<Object> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void produce() throws InterruptedException {
            synchronized (list) {
                while (list.size() == MAX_SIZE) {
                    list.wait();
                }
            }
            synchronized (list) {
                System.out.println("right");
                list.add(STUB);
                list.notify();
            }
        }
    }

    static class Consumer implements Runnable {
        private final List<Object> list;

        public Consumer(List<Object> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void consume() throws InterruptedException {
            synchronized (list) {
                while (list.isEmpty()) {
                    list.wait();
                }
            }
            synchronized (list) {
                System.out.println("left");
                list.remove(0);
                list.notify();
            }
        }
    }

    static final Object STUB = new Object();

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>(1);
        new Thread(new Consumer(list)).start();
        new Thread(new Producer(list)).start();

    }
}
