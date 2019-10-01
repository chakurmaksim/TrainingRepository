package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.impl.OrganisationDAO;
import by.training.certificationCenter.service.OrganisationService;
import by.training.certificationCenter.service.exception.ServiceException;

public class OrganisationServiceImpl implements OrganisationService<Organisation> {
    @Override
    public Organisation receiveOrgById(int orgId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            OrganisationDAO organisationDAO = factory.getOrganisationDAO();
            Organisation org = organisationDAO.findEntityById(orgId);
            factory.closeCertificationDAO(organisationDAO);
            return org;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
