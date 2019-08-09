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
     * @param item item
     */
    void remove(Matrix item) throws MatrixException;

    Matrix read() throws MatrixException, CloneNotSupportedException;

    void update(int row, int column, int newNum) throws MatrixException;
}
