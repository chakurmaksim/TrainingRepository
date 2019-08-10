package by.training.multithreading.bean.state;

import by.training.multithreading.bean.entity.Matrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatedState implements MatrixState {
    /**
     * Root logger.
     */
    private static Logger logger = LogManager.getRootLogger();

    /**
     * Next matrix state is modified, the main diagonal are filled zeros.
     *
     * @param item matrix object
     */
    @Override
    public void next(final Matrix item) {
        item.setState(new ModifiedState());
    }

    /**
     * Print to console that the matrix in its root state.
     *
     * @param item matrix object
     */
    @Override
    public void prev(final Matrix item) {
        logger.info("The matrix is in its root state.");
    }

    /**
     * Print to console the current status of the matrix: newly created.
     *
     * @param item matrix object
     */
    @Override
    public void printStatus(final Matrix item) {
        logger.info("The matrix is newly created.");
    }
}
