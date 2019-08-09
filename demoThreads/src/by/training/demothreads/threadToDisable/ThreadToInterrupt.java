package by.training.demothreads.threadToDisable;

import java.util.concurrent.TimeUnit;

public class ThreadToInterrupt implements Runnable {
    /**
     * Thread variable.
     */
    private Thread thread;

    /**
     * Constructor to create thread.
     *
     * @param name thread name
     */
    public ThreadToInterrupt(final String name) {
        thread = new Thread(this, name);
    }

    /**
     * Overriden run method.
     */
    @Override
    public void run() {
        System.out.printf("Thread %s have started... \n",
                Thread.currentThread().getName());
        int counter = 1; // counter
        while (true) {
            System.out.println("Cycle " + counter++);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread is interrupted "
                        + Thread.currentThread());
                break;
            }
        }
        System.out.printf("Thread %s have finished... \n",
                Thread.currentThread().getName());
    }

    /**
     * Start thread.
     */
    public void startThread() {
        thread.start();
    }

    /**
     * interrupt thread.
     */
    public void interruptThread() {
        thread.interrupt();
    }
}
