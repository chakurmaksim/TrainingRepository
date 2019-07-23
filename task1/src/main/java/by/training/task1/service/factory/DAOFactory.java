package by.training.task1.service.factory;

import by.training.task1.dao.filereader.FileWriteReader;

/**
 * Class that contains file instance.
 */
public final class DAOFactory {
    /**
     * Variable for keeping DAOFactory instance.
     */
    public static final DAOFactory SINGLE_INSTANCE = new DAOFactory();

    /**
     * Declaration and instantiating FileWriteReader instance.
     */
    private final FileWriteReader fileWriteReader = new FileWriteReader();

    private DAOFactory() {
    }

    /**
     * Get method.
     *
     * @return single instance DAOFactory
     */
    public static DAOFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    /**
     * Get method.
     *
     * @return instance of FileWriteReader
     */
    public FileWriteReader getFileWriteReader() {
        return fileWriteReader;
    }
}
