package by.training.multithreading.main;

import by.training.multithreading.controller.MatrixController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    /**
     * Number of matrix rows and columns.
     */
    private static final int ROW_COL_NUM = 12;
    /**
     * Maximum number for creating matrix values.
     */
    private static final int MAX_NUM = 10;
    /**
     * Number of threads.
     */
    private static final int THREAD_NUM = 4;
    private Main() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        Logger logger = LogManager.getRootLogger();
        MatrixController controller = new MatrixController();
        long timeStart = System.currentTimeMillis();
        controller.multiplyMatricesSingleThreaded(
                ROW_COL_NUM, ROW_COL_NUM, 1, MAX_NUM);
        long timeEnd = System.currentTimeMillis();
        String timeSingle = String.format("Time spent on creating and "
                + "multiplying two matrices in one thread, millis: %d",
                (timeEnd - timeStart));
        logger.info(timeSingle);
        timeStart = System.currentTimeMillis();
        controller.multiplyMatricesMultiThreaded(
                ROW_COL_NUM, ROW_COL_NUM, 1, MAX_NUM, THREAD_NUM);
        timeEnd = System.currentTimeMillis();
        String timeMulti = String.format("Time spent on creating and "
                        + "multiplying two matrices in multithreading, "
                + "millis: %d", (timeEnd - timeStart));
        logger.info(timeMulti);
        logger.info(controller.fillMainDiagonalConcurrently());
        logger.info(controller.fillMainDiagonalUsedAtomic());
    }
}
