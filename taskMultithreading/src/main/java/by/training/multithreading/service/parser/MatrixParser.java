package by.training.multithreading.service.parser;

import by.training.multithreading.bean.exception.MatrixException;

import java.util.LinkedList;
import java.util.List;

import static by.training.multithreading.bean.exception.MatrixException.getFileMatrixContentError;

public class MatrixParser {

    /**
     * Method to parse the matrix header.
     *
     * @param matrixTitle matrix header
     * @return array that contains the number of matrix rows and the number of
     * matrix columns respectively
     * @throws MatrixException when can not be parsed to an array
     */
    public int[] parseMatrixSize(String matrixTitle) throws MatrixException {
        int[] matrixSize = new int[2];
        try {
            String[] parts = matrixTitle.split(":");
            parts = parts[1].trim().split("x");
            matrixSize[0] = Integer.valueOf(parts[0].replace(
                    "\"", "").trim());
            matrixSize[1] = Integer.valueOf(parts[1].replace(
                    "\"}", "").trim());

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MatrixException(getFileMatrixContentError(), e);
        } catch (NumberFormatException e) {
            String message = String.format("%s: matrix size can not be "
                            + "converted to a number",
                    getFileMatrixContentError());
            throw new MatrixException(message, e);
        }
        return matrixSize;
    }

    /**
     * Method to parse the raw string in to the matrix values.
     * @param rawString row
     * @param size number of the matrix columns that is specified in the header
     * @return array of values
     * @throws MatrixException when raw string can not be parsed in to numbers
     */
    public int[] parseMatrixValues(String rawString, int size)
            throws MatrixException {
        int[] matrixValues = new int[size];
        String[] parts = rawString.split(" ");
        List<String> stringValues = new LinkedList<>();
        for (String part : parts) {
            part = part.trim();
            if (!part.equals("")) {
                stringValues.add(part);
            }
        }
        if (stringValues.size() == size) {
            for (int i = 0; i < size; i++) {
                try {
                    matrixValues[i] = Integer.valueOf(stringValues.get(i));
                } catch (NumberFormatException e) {
                    String message = String.format("%s: matrix row contains "
                                    + " not a number: %s",
                            getFileMatrixContentError(), rawString);
                    throw new MatrixException(message);
                }
            }
        } else {
            String message = String.format("%s: number of columns indicated in "
                            + "the header does not match the actual value "
                            + "in the matrix row: %s",
                    getFileMatrixContentError(), rawString);
            throw new MatrixException(message);
        }
        return matrixValues;
    }
}
