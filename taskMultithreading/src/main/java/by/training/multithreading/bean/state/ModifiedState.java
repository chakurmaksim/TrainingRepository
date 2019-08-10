package by.training.multithreading.bean.state;

import by.training.multithreading.bean.entity.Matrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModifiedState implements MatrixState {
    /**
     * Root logger.
     */
    private static Logger logger = LogManager.getRootLogger();

    /**
     * Next matrix state is completed, main diagonal are filled successfully.
     *
     * @param item matrix object
     */
    @Override
    public void next(final Matrix item) {
        item.setState(new CompletedState());
    }

    /**
     * Previous matrix state is newly created.
     *
     * @param item matrix object
     */
    @Override
    public void prev(final Matrix item) {
        item.setState(new CreatedState());
    }

    /**
     * Print to console the current status of the matrix: modified.
     *
     * @param item matrix object
     */
    @Override
    public void printStatus(final Matrix item) {
        logger.info("The matrix is modified: "
                + "the main diagonal has zeros.");
    }
}
