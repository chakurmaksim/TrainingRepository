package by.training.demothreads.threadProducerReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class ProducerConsumerApp {
    private ProducerConsumerApp() {
    }

    /**
     * main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class Store {
    /**
     * products in a stock.
     */
    private int product = 0;
    /**
     * Lock variable.
     */
    private ReentrantLock locker;
    /**
     * condition.
     */
    private Condition condition;

    Store() {
        locker = new ReentrantLock();
        condition = locker.newCondition();
    }

    public void get() {
        locker.lock();
        try {
            while (product < 1) {
                condition.await();
            }
            product--;
            System.out.println("Consumer bought 1 product");
            System.out.println("Products in stock: " + product);
            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock();
        }
    }

    public void put() {
        locker.lock();
        try {
            while (product >= 3) {
                condition.await();
            }
            product++;
            System.out.println("Producer added 1 product");
            System.out.println("Products in stock: " + product);
            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock();
        }
    }
}

class Producer implements Runnable {
    /**
     * Store variable.
     */
    private Store store;

    Producer(final Store newStore) {
        this.store = newStore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 6; i++) {
            store.put();
        }
    }
}

class Consumer implements Runnable {
    /**
     * Store variable.
     */
    private Store store;

    Consumer(final Store newStore) {
        this.store = newStore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 6; i++) {
            store.get();
        }
    }
}

