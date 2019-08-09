package by.training.demothreads.threadBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public final class RunBlocking {
    private RunBlocking() {
    }
    /**
     * main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);
        new Thread() {
            public void run() {
                for (int i = 1; i < 4; i++) {
                    try {
                        queue.put("Java" + i);
                        System.out.println("Element " + i + " added");
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(1_000);
                    System.out.println("Element " + queue.take() + " took");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
