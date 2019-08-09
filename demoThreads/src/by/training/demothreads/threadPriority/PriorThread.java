package by.training.demothreads.threadPriority;

import java.util.concurrent.TimeUnit;

public class PriorThread extends Thread {
    /**
     * iteration counter.
     */
    private int iterationCount;
    /**
     * time is used by a thread to be out.
     */
    private int timeSleep;

    /**
     * Constructor with one argument.
     *
     * @param name  thread name
     * @param count counter
     * @param timeout time out
     */
    public PriorThread(final String name, final int count, final int timeout) {
        super(name);
        this.iterationCount = count;
        this.timeSleep = timeout;
    }

    /**
     * run method is for a thread executing.
     */
    @Override
    public void run() {
        for (int i = 0; i < iterationCount; i++) {
            System.out.println(getName() + " " + i);
            try {
                TimeUnit.MILLISECONDS.sleep(timeSleep);
            } catch (InterruptedException e) {
                System.err.print(e);
            }
        }
    }
}
