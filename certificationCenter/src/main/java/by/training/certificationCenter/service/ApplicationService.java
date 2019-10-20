package by.training.certificationCenter.service;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.exception.ServiceException;

import java.io.FileInputStream;
import java.util.List;

public interface ApplicationService<T extends Application, V extends User> {
    List<T> receiveAppsByUser(V user, int pageNum, int pageLim)
            throws ServiceException;
    T showApplicationById(int applicationId, V user) throws ServiceException;
    boolean addNewApplication(T application) throws ServiceException;
    boolean deleteApplication(int applicationId, V user)
            throws ServiceException;
    boolean updateApplication(T application) throws ServiceException;
    FileInputStream receiveFileInputStream(String fullFileName)
            throws ServiceException;
    int receiveRowsNumber(V user) throws ServiceException;
}
