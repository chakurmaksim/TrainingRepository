package by.training.multithreading.service.executor;

import by.training.multithreading.bean.entity.Matrix;

/**
 * Class is for multiplying two matrices.
 */
public class MatrixMultiplier implements Runnable {
    /**
     * Variable to holding first matrix object.
     */
    private Matrix first;
    /**
     * Variable to holding second matrix object.
     */
    private Matrix second;
    /**
     * Variable to holding result matrix object.
     */
    private Matrix result;
    /**
     * The starting position of the row to fill the cells of the matrix.
     */
    private int startInd;
    /**
     * The finishing position of the row to fill the cells of the matrix.
     */
    private int endInd;

    /**
     * Constructor to initialize variables.
     *
     * @param newFirst    first matrix
     * @param newSecond   second matrix
     * @param newResult   result matrix
     * @param newStartInd starting position
     * @param newEndInd   finishing position
     */
    public MatrixMultiplier(
            final Matrix newFirst, final Matrix newSecond,
            final Matrix newResult, final int newStartInd,
            final int newEndInd) {
        first = newFirst;
        second = newSecond;
        result = newResult;
        startInd = newStartInd;
        endInd = newEndInd;
    }

    /**
     * The static method of single-threaded multiplication of two matrices.
     *
     * @param first first matrix
     * @param second second matrix
     * @param result result matrix
     */
    public static void multiplySingleThreaded(
            final Matrix first, final Matrix second, final Matrix result) {
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

    /**
     * Multi-threaded multiplication of two matrices.
     */
    public void multiplyMultiThreaded() {
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

    /**
     * Overridden method to start multi-threaded multiplication of two matrices.
     */
    @Override
    public void run() {
        multiplyMultiThreaded();
    }
}
