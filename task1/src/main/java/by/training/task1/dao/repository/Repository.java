package by.training.task1.dao.repository;

import by.training.task1.service.specification.Specification;

import java.util.List;

public interface Repository<T> {
    /**
     * Get a copy of the list of all items.
     * @return list of all the items.
     */
    List<T> readAll();

    /**
     * Add an item to the repository.
     *
     * @param item item
     */
    void add(T item);

    /**
     * Remove an item from the repository.
     *
     * @param item item
     */
    void remove(T item);

    /**
     * Update an item at the repository.
     *
     * @param item item
     */
    void update(T item);

    /**
     * Get items list according to the requirements.
     *
     * @param specification specification
     * @return list of items
     */
    List<T> query(Specification specification);
}
