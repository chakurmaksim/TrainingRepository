package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.Document;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.factory.DocumentFactory;
import by.training.certificationCenter.service.specification.DocumentsByAppIdSpecification;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO extends CertificationMySqlDAO<Document> {
    /**
     * The variable contains a database query to add a new document.
     */
    private static final String INSERT_DOC = "INSERT INTO documentation("
            + "application_id, storage) VALUES (?, ?)";
    /**
     * The variable contains a database query to remove a document by id.
     */
    private static final String REMOVE_DOC = "DELETE FROM documentation "
            + "WHERE id = ?";

    public DocumentDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<Document> findAll(int skip, int pageLim) throws DAOException {
        throw new DAOException(DAOException.getUnsupportedOperation(
                "findAll", "DocumentDAO"));
    }

    @Override
    public Document findEntityById(int id) throws DAOException {
        throw new DAOException(DAOException.getUnsupportedOperation(
                "findEntityById", "DocumentDAO"));
    }

    @Override
    public boolean remove(int id) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(REMOVE_DOC)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "DocumentDAO"), e);
        }
    }

    /**
     * Method allows to add a new document in to the database.
     *
     * @param entity Document instance
     * @return generated id by the database
     * @throws DAOException when occurs a database access error
     */
    @Override
    public int create(Document entity) throws DAOException {
        Connection connection = getConnection();
        int documentId = 0;
        try (PreparedStatement statement = connection.prepareStatement(
                INSERT_DOC, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getApplication().getId());
            statement.setString(2,
                    entity.getUploadFilePath() + entity.getFileName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                documentId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return documentId;
    }

    @Override
    public boolean update(Document entity) throws DAOException {
        throw new DAOException(DAOException.getUnsupportedOperation(
                "update", "DocumentDAO"));
    }

    /**
     * Method allows to get a specific query. In this case to find a limited
     * list of documents by application id.
     *
     * @param specification Specification instance
     * @return list of documents
     * @throws DAOException when occurs a database access error
     */
    @Override
    public List<Document> query(Specification specification)
            throws DAOException {
        List<Document> documentList = new ArrayList<>();
        if (specification instanceof DocumentsByAppIdSpecification) {
            DocumentsByAppIdSpecification
                    docsByAppId = (DocumentsByAppIdSpecification) specification;
            Connection connection = getConnection();
            DocumentFactory factory = DocumentFactory.getSingleInstance();
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                        docsByAppId.toSqlQuery())) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String filePath = resultSet.getString(2);
                        Document document = factory.createExistedDocument(
                                id, filePath);
                        documentList.add(document);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(getStatementError(
                        "DocumentDAO"), e);
            }
        }
        return documentList;
    }
}
