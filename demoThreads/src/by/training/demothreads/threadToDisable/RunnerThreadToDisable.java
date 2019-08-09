package by.training.demothreads.threadToDisable;

import java.util.concurrent.TimeUnit;

public final class RunnerThreadToDisable {
    private RunnerThreadToDisable() {
    }

    /**
     * Main method.
     *
     * @param args arguments.
     */
    public static void main(final String[] args) {
        System.out.printf("Method %s started ...\n",
                Thread.currentThread().getName());
        ThreadToDisable myThread = new ThreadToDisable();
        Thread myT = new Thread(myThread, "name of ThreadToDisable");
        myT.start();
        try {
            TimeUnit.MILLISECONDS.sleep(1100);
            myThread.disable();
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread is interrupted");
        }

        ThreadToInterrupt interruptTh = new ThreadToInterrupt(
                "name of interrupted thread");
        interruptTh.startThread();
        try {
            TimeUnit.MILLISECONDS.sleep(1100);
            interruptTh.interruptThread();
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread is interrupted "
                    + Thread.currentThread());
        }
        myThread.enable();
        Thread daemonTh = new Thread(myThread, "name of daemon Thread");
        daemonTh.setDaemon(true);
        daemonTh.start();
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Thread is interrupted "
                    + Thread.currentThread());
        }
        System.out.printf("Method %s finished ...\n",
                Thread.currentThread().getName());
    }
}
