package by.training.demothreads.threadProducerConsumer;

public final class ProducerConsumerApp {
    private ProducerConsumerApp() {
    }

    /**
     *main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Store store = new Store();
        new Producer(store).start();
        new Consumer(store).start();
    }

    static class Store {
        /**
         * goods counter.
         */
        int counter = 0;
        /**
         * maximum number.
         */
        final int N = 5;

        synchronized int put() {
            if (counter <= N) {
                counter++;
                System.out.println("store has " + counter + " goods");
                return 1;
            }
            return 0;
        }

        synchronized int get() {
            if (counter > 0) {
                counter--;
                System.out.println("store has " + counter + " goods");
                return 1;
            }
            return 0;
        }
    }

    static class Producer extends Thread {
        /**
         * Store instance.
         */
        Store store;
        /**
         * number of goods that need to add.
         */
        int product = 5;

        Producer(final Store newStore) {
            this.store = newStore;
        }

        public void run() {
            try {
                while (product > 0) {
                    product = product - store.put();
                    System.out.println("producer has to produce "
                            + product + " goods");
                    sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread of the producer was interrupted");
            }
        }
    }

    static class Consumer extends Thread {
        /**
         * Store instance.
         */
        Store store;
        /**
         * Current number of goods.
         */
        int product = 0; // текущее количество товаров со склада
        /**
         * maximum number.
         */
        final int N = 5; // максимально допустимое число

        Consumer(final Store newStore) {
            this.store = newStore;
        }

        public void run() {
            try {
                while (product < N) {
                    product = product + store.get();
                    System.out.println("Consumer bought " + product + " goods");
                    sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread of the consumer was interrupted");
            }
        }
    }
}
