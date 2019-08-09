package by.training.demothreads.threadCommonResourceSemaphore;

import java.util.concurrent.Semaphore;

public final class Runner {
    private Runner() {
    }
    /**
     * main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Semaphore sem = new Semaphore(1);
        CommonResource res = new CommonResource();
        new Thread(new CountThread(res, sem, "CountThread 1")).start();
        new Thread(new CountThread(res, sem, "CountThread 2")).start();
        new Thread(new CountThread(res, sem, "CountThread 3")).start();
    }
}
class CommonResource {
    /**
     *
     */
    private int x = 0;

    public int getX() {
        return x;
    }

    public void setX(final int newX) {
        x = newX;
    }
}

class CountThread implements Runnable {
    /**
     * common resource variable.
     */
    private CommonResource res;
    /**
     * semaphore variable.
     */
    private Semaphore sem;
    /**
     * thread name.
     */
    private String name;

    CountThread(final CommonResource newRes,
                final Semaphore newSem, final String newName) {
        this.res = newRes;
        this.sem = newSem;
        this.name = newName;
    }

    public void run() {

        try {
            System.out.println(name + " is waiting for the permission");
            sem.acquire();
            res.setX(1);
            for (int i = 1; i < 5; i++) {
                System.out.println(this.name + ": " + res.getX());
                int x = res.getX();
                x++;
                res.setX(x);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(name + " released permission");
        sem.release();
    }
}
