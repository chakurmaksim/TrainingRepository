package by.training.multithreading.bean.state;

import by.training.multithreading.bean.entity.Matrix;

/**
 * Pattern state.
 */
public interface MatrixState {
    /**
     * Next matrix state.
     *
     * @param item matrix object
     */
    void next(Matrix item);

    /**
     * Previous matrix state.
     *
     * @param item matrix object
     */
    void prev(Matrix item);

    /**
     * Print to console the current status.
     *
     * @param item item matrix object
     */
    void printStatus(Matrix item);
}
