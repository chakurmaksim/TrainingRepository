package by.training.multithreading.bean.entity;

import by.training.multithreading.bean.state.MatrixState;
import by.training.multithreading.bean.state.CreatedState;

import java.util.Arrays;

/**
 * Array storage class.
 */
public class Matrix implements Cloneable {
    /**
     * Two dimensional array.
     */
    private int[][] twoDimArray;
    /**
     * Matrix state.
     */
    private MatrixState state;

    /**
     * Constructor to create new Matrix instance.
     *
     * @param row    number of rows in an array
     * @param column number of columns in an array
     */
    public Matrix(final int row, final int column) {
        twoDimArray = new int[row][column];
        state = new CreatedState();
    }

    /**
     * Get method.
     *
     * @return number of rows in an array
     */
    public int getVerticalSize() {
        return twoDimArray.length;
    }

    /**
     * Get method.
     *
     * @return number of columns in an array
     */
    public int getHorizontalSize() {
        return twoDimArray[0].length;
    }

    /**
     * Matrix element get method.
     *
     * @param rowNum number of a row
     * @param colNum number of a column
     * @return matrix cell value in accordance with specified coordinates
     */
    public int getElement(final int rowNum, final int colNum) {
        return twoDimArray[rowNum][colNum];
    }

    /**
     * Matrix element set method.
     *
     * @param rowNum number of a row
     * @param colNum number of a column
     * @param val    value
     */
    public void setElement(final int rowNum, final int colNum, final int val) {
        twoDimArray[rowNum][colNum] = val;
    }

    /**
     * Get method.
     *
     * @return matrix state
     */
    public MatrixState getState() {
        return state;
    }

    /**
     * Set method.
     *
     * @param newState matrix state
     */
    public void setState(final MatrixState newState) {
        state = newState;
    }

    /**
     * Set new state.
     */
    public void nextState() {
        state.next(this);
    }

    /**
     * Set previous state.
     */
    public void previousState() {
        state.prev(this);
    }

    /**
     * Print current status.
     */
    public void printStatus() {
        state.printStatus(this);
    }

    /**
     * Overridden clone method.
     *
     * @return clone of the matrix.
     * @throws CloneNotSupportedException
     */
    @Override
    public Matrix clone() throws CloneNotSupportedException {
        Matrix clone = (Matrix) super.clone();
        clone.twoDimArray = new int[getVerticalSize()][getHorizontalSize()];
        for (int i = 0; i < getVerticalSize(); i++) {
            for (int j = 0; j < getHorizontalSize(); j++) {
                clone.twoDimArray[i][j] = getElement(i, j);
            }
        }
        return clone;
    }

    /**
     * Overridden equals method.
     *
     * @param newO instance of a Matrix class
     * @return true true if this and another object are equals
     */
    @Override
    public boolean equals(final Object newO) {
        if (this == newO) {
            return true;
        }
        if (newO == null || getClass() != newO.getClass()) {
            return false;
        }
        Matrix matrix = (Matrix) newO;
        return Arrays.equals(twoDimArray, matrix.twoDimArray);
    }

    /**
     * Overridden hashcode method.
     *
     * @return int number
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(twoDimArray);
    }

    /**
     * Overridden toString method.
     *
     * @return object string representation
     */
    @Override
    public String toString() {
        StringBuilder matrixStr = new StringBuilder();
        String title = String.format("\n{\"Matrix\" : \"%sx%s\"}\n",
                getVerticalSize(), getHorizontalSize());
        matrixStr.append(title);
        for (int[] row : twoDimArray) {
            for (int value : row) {
                matrixStr.append(value + " ");
            }
            matrixStr.append("\n");
        }
        return matrixStr.toString();
    }
}
