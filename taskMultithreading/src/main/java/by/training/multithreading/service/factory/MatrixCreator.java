package by.training.multithreading.service.factory;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.service.parser.MatrixParser;
import by.training.multithreading.service.validator.MatrixValidator;

import java.util.List;

import static by.training.multithreading.bean.exception.MatrixException.getFileMatrixContentError;

/**
 * Matrix creator class.
 */
public final class MatrixCreator {
    /**
     * Nested class to create MatrixCreator instance.
     */
    private static class MatrixCreatorHolder {
        /**
         * Variable for keeping MatrixCreator instance.
         */
        private static final MatrixCreator SINGLE_INSTANCE;

        static {
            SINGLE_INSTANCE = new MatrixCreator();
        }
    }

    /**
     * Declaration MatrixValidator instance.
     */
    private MatrixValidator validator;
    /**
     * Declaration MatrixParser instance.
     */
    private MatrixParser parser;

    private MatrixCreator() {
        validator = MatrixValidator.getMatrixValidator();
        parser = new MatrixParser();
    }

    /**
     * Get method.
     *
     * @return single instance of the MatrixCreator.
     */
    public static MatrixCreator getMatrixCreator() {
        return MatrixCreatorHolder.SINGLE_INSTANCE;
    }

    /**
     * Method to create new random Matrix instance
     *
     * @param row    number of rows in an array
     * @param column number of columns in an array
     * @param start  position to generate random values
     * @param end    position to generate random values
     * @return Matrix object which is filled with random values
     * @throws MatrixException if Matrix object can not be created
     */
    public Matrix createRandomMatrix(
            final int row,
            final int column,
            int start,
            int end) throws MatrixException {
        if (validator.checkRange(row, column)) {
            return fillRandomValues(new Matrix(row, column), start, end);
        } else {
            throw new MatrixException(MatrixException.getSizeError());
        }
    }

    /**
     * Method to fill array of a Matrix object with a random values.
     *
     * @param newMatrix Matrix instance
     * @param start     position to generate random values
     * @param end       position to generate random values
     * @return Matrix object which is filled with random values
     */
    private Matrix fillRandomValues(final Matrix newMatrix,
                                    final int start,
                                    final int end) {
        for (int i = 0; i < newMatrix.getVerticalSize(); i++) {
            for (int j = 0; j < newMatrix.getHorizontalSize(); j++) {
                int value = (int) (Math.random() * (end - start) + start);
                newMatrix.setElement(i, j, value);
            }
        }
        return newMatrix;
    }

    /**
     * Method creates a new matrix which is filled with zeros.
     *
     * @param row    number of rows
     * @param column number of columns
     * @return Matrix object
     * @throws MatrixException when can not pass validation
     */
    public Matrix createResultMatrix(final int row, final int column)
            throws MatrixException {
        if (validator.checkRange(row, column)) {
            return new Matrix(row, column);
        } else {
            throw new MatrixException(MatrixException.getSizeError());
        }
    }

    /**
     * Creates a new matrix from the list of rows that are read from the file.
     *
     * @param list of rows
     * @return Matrix object
     * @throws MatrixException when can not be parsed or passed validation
     */
    public Matrix createMatrixFromString(final List<String> list)
            throws MatrixException {
        if (list.size() == 0) {
            String message = String.format("%s: is empty",
                    getFileMatrixContentError());
            throw new MatrixException(message);
        }
        String matrixTitle = list.get(0).trim();
        Matrix matrix;
        if (matrixTitle.startsWith("{\"Matrix\"")) {
            int[] matrixSize = parser.parseMatrixSize(matrixTitle);
            matrix = createResultMatrix(matrixSize[0], matrixSize[1]);
            list.remove(0);
            if (list.size() == matrix.getVerticalSize()) {
                for (int i = 0; i < matrix.getVerticalSize(); i++) {
                    int[] values = parser.parseMatrixValues(list.get(i),
                            matrix.getHorizontalSize());
                    for (int j = 0; j < values.length; j++) {
                        matrix.setElement(i, j, values[j]);
                    }
                }
            } else {
                String message = String.format("%s: number of rows indicated in"
                                + " the header does not match the actual value "
                                + "in the matrix column: %s",
                        getFileMatrixContentError(), matrixTitle);
                throw new MatrixException(message);
            }
        } else {
            String message = String.format("%s: %s",
                    getFileMatrixContentError(), matrixTitle);
            throw new MatrixException(message);
        }
        return matrix;
    }
}
