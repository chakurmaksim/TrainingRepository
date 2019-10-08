package by.training.certificationCenter.service;

import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.service.exception.ServiceException;

import java.util.List;

public interface ProductService<P extends Product> {
    List<P> receiveProductsByAppId(int applicationId) throws ServiceException;
    boolean addNewProduct(P product) throws ServiceException;
    boolean deleteProduct(int applicationId) throws ServiceException;
    P updateProduct(P product) throws ServiceException;
}
