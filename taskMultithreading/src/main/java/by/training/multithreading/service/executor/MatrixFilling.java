package by.training.multithreading.service.executor;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.dao.repository.MatrixHolder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixFilling implements Runnable {
    private final int threadNum;
    private MatrixHolder matrixHolder;
    private ReentrantLock locker;
    public MatrixFilling(
            final int newThreadNum,
            final MatrixHolder commonResource,
            ReentrantLock newLocker) {
        this.threadNum = newThreadNum;
        this.matrixHolder = commonResource;
        this.locker = newLocker;
    }

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
                putCondition.await(5, TimeUnit.MILLISECONDS);
                matrixHolder.update(i, i, threadNum);
                putCondition.signalAll();
            } catch (InterruptedException e) {

            } catch (MatrixException e) {

            } finally {
                locker.unlock();
            }
        }
    }
}
