package by.training.multithreading.service.executor;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.dao.repository.MatrixHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class to create Runnable objects for filling matrix diagonal with zeros.
 */
public class MatrixFillConcurrently implements Runnable {
    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(
            MatrixFillConcurrently.class);
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
     * ReentrantLock instance.
     */
    private ReentrantLock locker;

    /**
     * To initialize unique number, matrixHolder and locker variables.
     *
     * @param newThreadNum unique thread number
     * @param commonResource matrix keeper
     * @param newLocker locker
     */
    public MatrixFillConcurrently(
            final int newThreadNum,
            final MatrixHolder commonResource,
            final ReentrantLock newLocker) {
        this.threadNum = newThreadNum;
        this.matrixHolder = commonResource;
        this.locker = newLocker;
    }

    /**
     * Overridden method where the matrix will be ​​filled with unique values.
     */
    @Override
    public void run() {
        Matrix matrix = null;
        try {
            matrix = matrixHolder.read();
        } catch (MatrixException e) {

        } catch (CloneNotSupportedException e) {
            return;
        }
        Condition putCondition = locker.newCondition();
        for (int i = 0; i < matrix.getVerticalSize(); i++) {
            try {
                locker.lock();
                putCondition.await(TIME_AWAIT, TimeUnit.MILLISECONDS);
                matrixHolder.update(i, i, threadNum);
                putCondition.signalAll();
            } catch (InterruptedException e) {
                String message = String.format("%s %s", e.toString(),
                        Thread.currentThread().toString());
                logger.error(message);
            } catch (MatrixException e) {
                logger.error(e.toString());
            } finally {
                locker.unlock();
            }
        }
    }
}
