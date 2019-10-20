package by.training.certificationCenter.dao;

import by.training.certificationCenter.dao.exception.DAOException;

public interface PaginationDAO {
    int getRowsNumber(int userId) throws DAOException;
    int getRowsNumber() throws DAOException;
}
