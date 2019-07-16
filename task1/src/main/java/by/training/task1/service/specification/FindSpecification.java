package by.training.task1.service.specification;

public interface FindSpecification<T> extends Specification {
    /**
     * Find by certain signs.
     * @param entity entity
     * @return boolean value
     */
    boolean findSpecified(T entity);
}
