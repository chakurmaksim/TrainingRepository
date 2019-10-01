package by.training.certificationCenter.service;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.exception.ServiceException;

public interface UserService<V extends User> {
    V signIn(String login, String password) throws ServiceException;
    V receiveUserById(int userId) throws ServiceException;
    boolean registration(V user) throws ServiceException;
}
