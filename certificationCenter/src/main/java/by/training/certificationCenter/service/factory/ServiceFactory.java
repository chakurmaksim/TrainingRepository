package by.training.certificationCenter.service.factory;

import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.OrganisationService;
import by.training.certificationCenter.service.ProductService;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.impl.*;

public class ServiceFactory {
    private ServiceFactory() {
    }

    private static class ServiceFactoryHolder {
        private static final ServiceFactory SINGLE_INSTANCE;
        static {
            SINGLE_INSTANCE = new ServiceFactory();
        }
    }

    public static ServiceFactory getInstance() {
        return ServiceFactoryHolder.SINGLE_INSTANCE;
    }

    public ApplicationService getApplicationService() {
        return new ApplicationServiceImpl();
    }

    public ProductService getProductService() {
        return new ProductServiceImpl();
    }

    public UserService getUserService() {
        return new UserServiceImpl();
    }

    public OrganisationService getOrganisationService() {
        return new OrganisationServiceImpl();
    }
}
