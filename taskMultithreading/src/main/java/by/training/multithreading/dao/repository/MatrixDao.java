package by.training.multithreading.dao.repository;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;

public interface MatrixDao {
    /**
     * Add an item to the repository.
     *
     * @param item item
     */
    void add(Matrix item);

    /**
     * Remove an item from the repository.
     *
     * @param item matrix object
     * @throws MatrixException if can not remove
     */
    void remove(Matrix item) throws MatrixException;

    /**
     * Method to provide access to common matrix.
     *
     * @return Matrix object
     * @throws MatrixException            when matrix does not exist
     * @throws CloneNotSupportedException if matrix can not be cloned
     */
    Matrix read() throws MatrixException, CloneNotSupportedException;

    /**
     * Method is to update the common matrix.
     *
     * @param row    number of the row
     * @param column number of the column
     * @param newNum new value
     * @throws MatrixException if matrix can not be updated
     */
    void update(int row, int column, int newNum) throws MatrixException;
}
