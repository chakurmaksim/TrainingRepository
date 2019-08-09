package by.training.multithreading.service.executor;

import by.training.multithreading.bean.entity.Matrix;

/**
 * Class is for multiplying two matrices.
 */
public class MatrixMultiplier implements Runnable {
    private Matrix first;
    private Matrix second;
    private Matrix result;
    private int startInd;
    private int endInd;

    public MatrixMultiplier(final Matrix newFirst,
                            final Matrix newSecond,
                            final Matrix newResult,
                            final int newStartInd,
                            final int newEndInd) {
        first = newFirst;
        second = newSecond;
        result = newResult;
        startInd = newStartInd;
        endInd = newEndInd;
    }

    public static void multiplySingleTh(Matrix first,
                                        Matrix second,
                                        Matrix result) {
        for (int i = 0; i < first.getVerticalSize(); i++) {
            for (int j = 0; j < second.getHorizontalSize(); j++) {
                int value = 0;
                for (int k = 0; k < first.getHorizontalSize(); k++) {
                    value += first.getElement(i, k) * second.getElement(k, j);
                }
                result.setElement(i, j, value);
            }
        }
    }

    public void multiplyMultiTh() {
        for (int i = startInd; i < endInd; i++) {
            for (int j = 0; j < second.getHorizontalSize(); j++) {
                int value = 0;
                for (int k = 0; k < first.getHorizontalSize(); k++) {
                    value += first.getElement(i, k) * second.getElement(k, j);
                }
                result.setElement(i, j, value);
            }
        }
    }

    @Override
    public void run() {
        multiplyMultiTh();
    }
}
