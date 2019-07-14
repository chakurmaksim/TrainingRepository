package by.training.task1.service.specification;

public interface FindSpecification<T> extends Specification {
    boolean findSpecified(T entity);
}
