package by.training.demothreads.threadCommonResourceReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public final class Runner {
    private Runner() {
    }
    /**
     * Main method.
     * @param args arguments.
     */
    public static void main(final String[] args) {
        CommonResource commonResource = new CommonResource();
        ReentrantLock locker = new ReentrantLock();
        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(new CountThread(commonResource, locker));
            t.setName("Поток " + i);
            t.start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException newE) {
            newE.printStackTrace();
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

class CountThread implements Runnable {
    /**
     * common resource variable.
     */
    private CommonResource res;
    /**
     * ReentrantLock variable.
     */
    private ReentrantLock locker;

    /**
     * Constructor.
     *
     * @param newRes CommonResource instance
     * @param newLocker ReentrantLock instance
     */
    CountThread(final CommonResource newRes, final ReentrantLock newLocker) {
        this.res = newRes;
        this.locker = newLocker;
    }

    public void run() {
        locker.lock();
        try {
            res.setX(1);
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s %d \n",
                        Thread.currentThread().getName(), res.getX());
                int x = res.getX();
                x++;
                res.setX(x);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
        } finally {
            locker.unlock();
        }
    }
}