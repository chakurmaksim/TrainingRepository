package by.training.task1.service.specification;

import java.util.List;

public interface SortSpecification<T> extends Specification {
    void sortSpecifiedComparator(List<T> entity);
}
