package by.training.taskComposite.dao;

import by.training.task1.service.specification.Specification;

public interface TextRepository<T> {
    /**
     * Get a copy of the item
     *
     * @return item.
     */
    String read();

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
     * Get item according to the requirements.
     *
     * @param specification specification
     * @return item
     */
    String query(Specification specification);
}
