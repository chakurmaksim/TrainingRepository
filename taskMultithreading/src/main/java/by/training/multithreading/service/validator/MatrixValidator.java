package by.training.multithreading.service.validator;

import by.training.multithreading.bean.entity.Matrix;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class contains methods to validate matrices.
 */
public final class MatrixValidator implements Cloneable, Serializable {
    /**
     * Variable is used during serialization.
     */
    private static final long SERIAL_VERSION_UID;
    /**
     * Minimum matrix size.
     */
    private static final int MIN_MATRIX_SIZE = 8;
    /**
     * Maximum matrix size.
     */
    private static final int MAX_MATRIX_SIZE = 12;
    /**
     * Single instance of the MatrixValidator object.
     */
    private static MatrixValidator singleInstance;
    /**
     * single instance of the ReentrantLock object.
     */
    private static final ReentrantLock REENTRANT_LOCK;

    static {
        SERIAL_VERSION_UID = UUID.randomUUID().getMostSignificantBits();
        REENTRANT_LOCK = new ReentrantLock();
    }

    private MatrixValidator() {
    }

    /**
     * @return single instance of the MatrixValidator object.
     */
    public static MatrixValidator getMatrixValidator() {
        if (singleInstance == null) {
            REENTRANT_LOCK.lock();
            if (singleInstance == null) {
                singleInstance = new MatrixValidator();
            }
            REENTRANT_LOCK.unlock();
        }
        return singleInstance;
    }

    /**
     * Overridden clone method.
     *
     * @return single instance of the MatrixValidator object.
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return singleInstance;
    }

    protected Object readResolve() {
        return singleInstance;
    }

    /**
     * Check size of the matrix in accordance with the requirements of the task.
     *
     * @param row    number of rows in the matrix
     * @param column number of columns in the matrix
     * @return true if size of the matrix corresponds to the task
     */
    public boolean checkRange(final int row, final int column) {
        if (row >= MIN_MATRIX_SIZE && row <= MAX_MATRIX_SIZE && column == row) {
            return true;
        }
        return false;
    }

    /**
     * Check equality lengths of two two-dimensional arrays.
     *
     * @param first  Matrix instance
     * @param second Matrix instance
     * @return true if horizontal length of the first double-dimensional array
     * equals vertical length of the second double-dimensional array.
     */
    public boolean couldMultiply(final Matrix first, final Matrix second) {
        return first.getHorizontalSize() == second.getVerticalSize();
    }

    /**
     * Check existing zeros in the main diagonal of the matrix.
     *
     * @param matrix object
     * @return true if the main diagonal of the matrix does not contain zeros
     */
    public boolean validateMatrixDiagonal(final Matrix matrix) {
        for (int i = 0; i < matrix.getVerticalSize(); i++) {
            if (matrix.getElement(i, i) == 0) {
                return false;
            }
        }
        return true;
    }
}
