package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.CertificationEntity;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.specification.Specification;

import java.util.List;

public interface CertificationDAO<T extends CertificationEntity> {
    List<T> findAll(int skip, int pageLim) throws DAOException;
    T findEntityById(int id) throws DAOException;
    boolean remove(int id) throws DAOException;
    boolean remove(T entity) throws DAOException;
    int create(T entity) throws DAOException;
    T update(T entity) throws DAOException;
    List<T> query(Specification specification) throws DAOException;
}
