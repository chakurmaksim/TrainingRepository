package by.training.task1.service.factory;

import by.training.task1.dao.filereader.FileWriteReader;

/**
 * Class that contains file instance.
 */
public final class FileReaderFactory {
    /**
     * Variable for keeping FileReaderFactory instance.
     */
    public static final FileReaderFactory SINGLE_INSTANCE = new FileReaderFactory();

    /**
     * Declaration and instantiating FileWriteReader instance.
     */
    private final FileWriteReader fileWriteReader = new FileWriteReader();

    private FileReaderFactory() {
    }

    /**
     * Get method.
     *
     * @return single instance FileReaderFactory
     */
    public static FileReaderFactory getSingleInstance() {
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
