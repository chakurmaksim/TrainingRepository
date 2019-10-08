package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.impl.ProductDAO;
import by.training.certificationCenter.service.ProductService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.specification.ProductsByAppIdSpecification;
import by.training.certificationCenter.service.specification.Specification;

import java.util.List;

public class ProductServiceImpl implements ProductService<Product> {
    @Override
    public List<Product> receiveProductsByAppId(int applicationId)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            ProductDAO productDAO = factory.getProductDAO();
            Specification prodByAppIdSpec =
                    new ProductsByAppIdSpecification(applicationId);
            List<Product> products = productDAO.query(prodByAppIdSpec);
            factory.closeCertificationDAO(productDAO);
            return products;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean addNewProduct(Product product) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            ProductDAO productDAO = factory.getProductDAO();
            productDAO.create(product);
            factory.closeCertificationDAO(productDAO);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean deleteProduct(int applicationId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            ProductDAO productDAO = factory.getProductDAO();
            productDAO.remove(applicationId);
            factory.closeCertificationDAO(productDAO);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    @Override
    public Product updateProduct(Product product) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        Product updatedProd;
        try {
            ProductDAO productDAO = factory.getProductDAO();
            updatedProd = productDAO.update(product);
            factory.closeCertificationDAO(productDAO);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return updatedProd;
    }
}
