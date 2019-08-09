package by.training.demothreads.threadToDisable;

import java.util.concurrent.TimeUnit;

public class ThreadToDisable implements Runnable {
    /**
     * Flag variable.
     */
    private boolean isActive;

    /**
     * set the isActive variable to false.
     */
    void disable() {
        isActive = false;
    }

    /**
     * set the isActive variable to true.
     */
    void enable() {
        isActive = true;
    }

    /**
     * set the isActive variable to true.
     */
    ThreadToDisable() {
        isActive = true;
    }

    /**
     * overridden run method.
     */
    @Override
    public void run() {
        System.out.printf("Thread %s have started... \n",
                Thread.currentThread().getName());
        int counter = 1; // counter
        while (isActive) {
            System.out.println("Cycle " + counter++);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread is interrupted");
            }
        }
        System.out.printf("Thread %s have finished... \n",
                Thread.currentThread().getName());
    }
}
