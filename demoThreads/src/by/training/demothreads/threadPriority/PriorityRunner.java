package by.training.demothreads.threadPriority;

public final class PriorityRunner {
    /**
     * Number of iterations.
     */
    private static final int ITERATION_NUM = 50;
    private PriorityRunner() {
    }

    /**
     * method is the main.
     * @param args arguments
     */
    public static void main(final String[] args) {
        PriorThread min = new PriorThread("Min", ITERATION_NUM, 10);
        PriorThread max = new PriorThread("Max", ITERATION_NUM, 10);
        PriorThread norm = new PriorThread("Norm", ITERATION_NUM, 10);
        min.setPriority(Thread.MIN_PRIORITY); // 1
        max.setPriority(Thread.MAX_PRIORITY); // 10
        norm.setPriority(Thread.NORM_PRIORITY); // 5
        min.start();
        norm.start();
        max.start();
    }
}
