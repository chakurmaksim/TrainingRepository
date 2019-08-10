package by.training.multithreading.bean.state;

import by.training.multithreading.bean.entity.Matrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompletedState implements MatrixState {
    /**
     * Root logger.
     */
    private static Logger logger = LogManager.getRootLogger();

    /**
     * Print to console that the matrix in its final state.
     *
     * @param item matrix object
     */
    @Override
    public void next(final Matrix item) {
        logger.info("The matrix is in its final state.");
    }

    /**
     * Previous matrix state is modified, the main diagonal are filled zeros
     * or not filled successfully.
     *
     * @param item matrix object
     */
    @Override
    public void prev(final Matrix item) {
        item.setState(new ModifiedState());
    }

    /**
     * Print to console the current status of the matrix: fully completed.
     *
     * @param item matrix object
     */
    @Override
    public void printStatus(final Matrix item) {
        logger.info("The matrix is modified successfully "
                + "and fully completed.");
    }
}
