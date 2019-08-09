package by.training.demothreads.threadExchanger;

public class Consumer extends Subject implements Runnable {
    /**
     * Constructor with two arguments.
     * @param name producer name
     * @param item goods
     */
    public Consumer(final String name, final Item item) {
        super(name, item);
    }

    /**
     * Overridden run method.
     */
    @Override
    public void run() {
        try {
            synchronized (item) {
                int requiredNumber = item.getNumber();
                item = exchanger.exchange(item);
                if (requiredNumber >= item.getNumber()) {
                    System.out.println("Consumer " + getName()
                            + " increases cost of the goods");
                } else {
                    System.out.println("Consumer " + getName()
                            + " decreases cost of the goods");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
