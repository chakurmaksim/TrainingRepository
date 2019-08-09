package by.training.demothreads.threadProducerConsumerWaitNotify;

public final class ProducerConsumerApp {
    private ProducerConsumerApp() {
    }
    /**
     * Main method.
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
     * quantity of the products.
     */
    private int product = 0;

    public synchronized void put() {
        while (product >= 3) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        product++;
        System.out.println("Producer added 1 product");
        System.out.println("Products in stock: " + product);
        notify();
    }

    public synchronized void get() {
        while (product < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        product--;
        System.out.println("Consumer bought 1 product");
        System.out.println("Products in stock: " + product);
        notify();
    }
}
class Producer implements Runnable {
    /**
     * Variable contains Store instance.
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
     * Variable contains Store instance.
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

