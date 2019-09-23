package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.CertificationEntity;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.SQLException;
import java.util.List;

public interface CertificationDAO<T extends CertificationEntity> {
    List<T> findAll();
    T findEntityById(int id) throws SQLException;
    boolean remove(int id);
    boolean remove(T entity);
    boolean create(T entity);
    T update(T entity);
    List<T> query(Specification specification);
}
