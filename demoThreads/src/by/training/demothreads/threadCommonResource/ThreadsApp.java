package by.training.demothreads.threadCommonResource;

import java.util.concurrent.TimeUnit;

public final class ThreadsApp {
    private ThreadsApp() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        CommonResource commonResource = new CommonResource();
        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(new CountThread(commonResource));
            t.setName("Поток " + i);
            t.start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException newE) {
            newE.printStackTrace();
        }
        System.out.println();

        CommonResourceWithIncrementMethod commonResource2 =
                new CommonResourceWithIncrementMethod();
        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(
                    new CountThreadThroughIncrementMethod(commonResource2));
            t.setName("Поток " + i);
            t.start();
        }
    }
}

class CommonResource {
    /**
     * variable which is owned to Common resource object.
     */
    private int x = 0;

    int getX() {
        return x;
    }

    void setX(final int newX) {
        x = newX;
    }
}

class CommonResourceWithIncrementMethod {
    /**
     * variable which is owned to Common resource object.
     */
    private int x;
    synchronized void increment() {
        x = 1;
        for (int i = 1; i < 5; i++) {
            System.out.printf("%s %d \n", Thread.currentThread().getName(), x);
            x++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}

class CountThread implements Runnable {
    /**
     * common resource variable.
     */
    private CommonResource res;

    /**
     * Constructor.
     *
     * @param newRes CommonResource instance
     */
    CountThread(final CommonResource newRes) {
        this.res = newRes;
    }

    public void run() {
        synchronized (res) {
            res.setX(1);
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s %d \n",
                        Thread.currentThread().getName(), res.getX());
                int x = res.getX();
                x++;
                res.setX(x);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}

class CountThreadThroughIncrementMethod implements Runnable {
    /**
     * common resource variable.
     */
    private CommonResourceWithIncrementMethod res;

    /**
     * Constructor.
     *
     * @param newRes CommonResource instance
     */
    CountThreadThroughIncrementMethod(
            final CommonResourceWithIncrementMethod newRes) {
        this.res = newRes;
    }

    public void run() {
        res.increment();
    }
}
