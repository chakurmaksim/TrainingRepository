package by.training.multithreading.dao.repository;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static by.training.multithreading.bean.exception.MatrixException.getNullableError;

public enum MatrixHolder implements MatrixDao {
    /**
     * single instance of the MatrixHolder.
     */
    SINGLE_INSTANCE;

    /**
     * Matrix object is wrapped to Optional instance.
     */
    private Optional<Matrix> matrixOpt = Optional.empty();
    /**
     * matrix rows counter.
     */
    private AtomicInteger rowsCounter;

    @Override
    public void add(final Matrix item) {
        matrixOpt = Optional.of(item);
        rowsCounter = new AtomicInteger(0);
    }

    @Override
    public void remove(final Matrix item) throws MatrixException {
        Matrix matrix = matrixOpt.orElseThrow(() ->
                new MatrixException(getNullableError()));
        if (matrix.equals(item)) {
            matrixOpt = Optional.empty();
        }
    }

    @Override
    public Matrix read() throws MatrixException,
            CloneNotSupportedException {
        Matrix matrix = matrixOpt.orElseThrow(() ->
                new MatrixException(getNullableError()));
        return matrix.clone();
    }


    @Override
    public void update(final int row, final int column, final int newNum)
            throws MatrixException {
        Matrix matrix = matrixOpt.orElseThrow(() ->
                new MatrixException(getNullableError()));
        if (row == column && matrix.getElement(row, column) == 0) {
            matrix.setElement(row, column, newNum);
        }
    }

    /**
     * Get atomic rows counter.
     *
     * @return number of the matrix row
     * @throws MatrixException if matrix object is null
     */
    public int getRowsCounter() throws MatrixException {
        Matrix matrix = matrixOpt.orElseThrow(() ->
                new MatrixException(getNullableError()));
        return rowsCounter.get() < matrix.getVerticalSize()
                ? rowsCounter.getAndIncrement() : -1;
    }
}
