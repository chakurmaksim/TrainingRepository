package by.training.demothreads.threadManagement;

public final class RunnerJoin {
    private RunnerJoin() {
    }
    static {
        System.out.println("Main thread have started.");
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        JoinThread t1 = new JoinThread("First");
        JoinThread t2 = new JoinThread("Second");
        t1.start();
        t2.start();
        try {
            t1.join(); // main thread have been stopped before finishing t1 thread.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread finished "
                + Thread.currentThread().getName());
    }
}
