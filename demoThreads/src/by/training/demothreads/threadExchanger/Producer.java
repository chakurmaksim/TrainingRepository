package by.training.demothreads.threadExchanger;

public class Producer extends Subject implements Runnable {
    /**
     * Constructor with two arguments.
     * @param name producer name
     * @param item goods
     */
    public Producer(final String name, final Item item) {
        super(name, item);
    }

    /**
     * Overridden run method.
     */
    @Override
    public void run() {
        try {
            synchronized (item) {
                int proposedNumber = this.getItem().getNumber();
                item = exchanger.exchange(item);
                if (proposedNumber <= item.getNumber()) {
                    System.out.println("Producer " + getName()
                            + " increases his plan of production");
                } else {
                    System.out.println("Producer " + getName()
                            + " decreases his plan of production");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
