package by.training.multithreading.service.executor;

import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.dao.repository.MatrixHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class MatrixFillUsedAtomicInt implements Runnable {
    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(
            MatrixFillUsedAtomicInt.class);
    /**
     * Time to pause thread execution.
     */
    private static final int TIME_AWAIT = 5;
    /**
     * Unique number of the thread.
     */
    private final int threadNum;
    /**
     * Common resource which is keeping Matrix object.
     */
    private MatrixHolder matrixHolder;

    /**
     * To initialize unique number, matrixHolder variable.
     *
     * @param newThreadNum unique thread number
     * @param commonResource matrix keeper
     */
    public MatrixFillUsedAtomicInt(final int newThreadNum,
                                   final MatrixHolder commonResource) {
        this.threadNum = newThreadNum;
        this.matrixHolder = commonResource;
    }

    /**
     * Overridden method where the matrix will be ​​filled with unique values.
     */
    @Override
    public void run() {
        int matrixRow = -1;
        do {
            try {
                matrixRow = matrixHolder.getRowsCounter();
                if (matrixRow != -1) {
                    matrixHolder.update(matrixRow, matrixRow, threadNum);
                }
                TimeUnit.MILLISECONDS.sleep(TIME_AWAIT);
            } catch (MatrixException e) {
                logger.error(e.toString());
            } catch (InterruptedException e) {
                String message = String.format("%s %s", e.toString(),
                        Thread.currentThread().toString());
                logger.error(message);
            }
        } while (matrixRow != -1);
    }
}
