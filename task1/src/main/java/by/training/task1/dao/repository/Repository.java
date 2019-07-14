package by.training.task1.dao.repository;

import by.training.task1.service.specification.Specification;

import java.util.List;

public interface Repository<T> {
    void add(T item);
    void remove(T item);
    void update(T item);
    List<T> query(Specification specification);
}
