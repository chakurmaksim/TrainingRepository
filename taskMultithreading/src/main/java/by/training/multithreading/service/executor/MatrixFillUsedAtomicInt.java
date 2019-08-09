package by.training.multithreading.service.executor;

import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.dao.repository.MatrixHolder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixFillUsedAtomicInt implements Runnable {
    private final int threadNum;
    private MatrixHolder matrixHolder;
    private ReentrantLock locker;
    public MatrixFillUsedAtomicInt(final int newThreadNum,
                            final MatrixHolder commonResource,
                            ReentrantLock newLocker) {
        this.threadNum = newThreadNum;
        this.matrixHolder = commonResource;
        this.locker = newLocker;
    }

    @Override
    public void run() {
        int matrixRow = -1;
        do {
            try {
                matrixRow = matrixHolder.getRowsCounter();
                if (matrixRow != -1) {
                    matrixHolder.update(matrixRow, matrixRow, threadNum);
                }
                TimeUnit.MILLISECONDS.sleep(5);
                Thread.currentThread().interrupt();
            } catch (MatrixException e) {

            } catch (InterruptedException e) {

            }
        } while (matrixRow != -1);
    }
}
