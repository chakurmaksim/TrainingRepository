package by.training.task1.service.specification;

import java.util.List;

public interface SortSpecification<T> extends Specification {
    /**
     * Sort collection.
     * @param entity list of entities
     */
    void sortSpecifiedComparator(List<T> entity);
}
