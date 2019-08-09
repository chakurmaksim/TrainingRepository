package by.training.demothreads.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public final class SafeCounterWithoutLock {
    private SafeCounterWithoutLock() {
    }
    /**
     * AtomicInteger instance.
     */
    private static AtomicInteger at = new AtomicInteger(0);
    /**
     * main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        Thread t1 = new Thread(new MyRunnable());
        Thread t2 = new Thread(new MyRunnable());
        t1.start();
        t2.start();
    }

    static class MyRunnable implements Runnable {
        /**
         * current counter.
         */
        private int myCounter;
        /**
         * previous counter.
         */
        private int myPrevCounter;
        /**
         * counter plus five.
         */
        private int myCounterPlusFive;
        /**
         * flag variable.
         */
        private boolean isNine;

        @Override
        public void run() {
            myCounter = at.incrementAndGet();
            System.out.println("Thread " + Thread.currentThread().getId()
                    + " Counter : " + myCounter);
            myPrevCounter = at.getAndIncrement();
            System.out.println("Thread " + Thread.currentThread().getId()
                    + " Previous : " + myPrevCounter);
            myCounterPlusFive = at.addAndGet(5);
            System.out.println("Thread " + Thread.currentThread().getId()
                    + " Plus five : " + myCounterPlusFive);
            isNine = at.compareAndSet(9, 3);
            if (isNine) {
                System.out.println("Thread " + Thread.currentThread().getId()
                        + " Value was equal to 9, so it was updated to "
                        + at.intValue());
            }
        }
    }
}
