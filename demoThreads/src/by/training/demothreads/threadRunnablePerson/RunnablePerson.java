package by.training.demothreads.threadRunnablePerson;

/**
 * RunnablePerson class contains run method to start separate thread.
 */
public class RunnablePerson extends Person implements Runnable {
    /**
     * number of iterations.
     */
    private int iterationCounter;

    /**
     * Constructor with one argument.
     * @param newName person name.
     * @param count number of iterations.
     */
    public RunnablePerson(final String newName, final int count) {
        super(newName);
        this.iterationCounter = count;
    }

    /**
     * overridden run method.
     */
    @Override
    public void run() {
        for (int i = 0; i < iterationCounter; i++) {
            System.out.println(getName() + ": \"Hello World!\"");
        }
    }
}
