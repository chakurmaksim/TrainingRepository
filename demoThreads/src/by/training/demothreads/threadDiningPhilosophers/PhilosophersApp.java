package by.training.demothreads.threadDiningPhilosophers;

import java.util.concurrent.Semaphore;

public final class PhilosophersApp {
    private PhilosophersApp() {
    }

    /**
     * main method.
     *
     * @param args arguments.
     */
    public static void main(final String[] args) {
        Semaphore sem = new Semaphore(2);
        for (int i = 1; i < 6; i++) {
            new Philosopher(sem, i).start();
        }
    }
}

class Philosopher extends Thread {
    /**
     * semaphore limiting the number of philosophers.
     */
    private Semaphore sem;
    /**
     * number of meals.
     */
    private int num = 0;
    /**
     * conditional number of the philosopher.
     */
    private int id;

    Philosopher(final Semaphore newSem, final int newId) {
        this.sem = newSem;
        this.id = newId;
    }

    public void run() {
        try {
            while (num < 3) {
                sem.acquire();
                System.out.println("Philosopher " + id
                        + " sits down at the table");
                sleep(500);
                num++;
                System.out.println("Philosopher " + id + " left the table");
                sem.release();
                sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Philosopher " + id
                    + " has problems with his helth");
        }
    }
}

