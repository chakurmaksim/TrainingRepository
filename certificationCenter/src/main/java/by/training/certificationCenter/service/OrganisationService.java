package by.training.certificationCenter.service;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.service.exception.ServiceException;

public interface OrganisationService<K extends Organisation> {
    K receiveOrgById(int orgId) throws ServiceException;
}
