package by.training.certificationCenter.dao.filereader;

import by.training.certificationCenter.dao.exception.DAOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.locks.ReentrantLock;

public final class FileWriteReader {
    /**
     * Single instance of the FileWriteReader object.
     */
    private static FileWriteReader singleInstance;
    /**
     * Single instance of the ReentrantLock object.
     */
    private static final ReentrantLock REENTRANT_LOCK;

    static {
        REENTRANT_LOCK = new ReentrantLock();
    }

    private FileWriteReader() {
    }

    /**
     * Get method.
     *
     * @return single instance of the FileWriteReader object.
     */
    public static FileWriteReader getSingleInstance() {
        if (singleInstance == null) {
            try {
                REENTRANT_LOCK.lock();
                if (singleInstance == null) {
                    singleInstance = new FileWriteReader();
                }
            } finally {
                REENTRANT_LOCK.unlock();
            }
        }
        return singleInstance;
    }

    public void writeDocument(
            final String uploadFilePath, final String fileName,
            final InputStream inputStream)
            throws DAOException {
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        File file = new File(uploadFilePath, fileName);
        file.deleteOnExit();
        try {
            Files.copy(inputStream, file.toPath());
        } catch (IOException e) {
            String errorMessage = String.format(
                    "Document can not be written in to the file \"%s\"",
                    fileName);
            throw new DAOException(errorMessage, e);
        }
    }

    public InputStream getInputStream(final String fullFileName)
            throws DAOException {
        try {
            return new FileInputStream(fullFileName);
        } catch (FileNotFoundException e) {
            String errorMessage = String.format(
                    "File name \"%s\" can not be found", fullFileName);
            throw new DAOException(errorMessage, e);
        }
    }

    public boolean deleteFile(final String fullFileName) throws DAOException {
        File file = new File(fullFileName);
        try {
            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (SecurityException e) {
            String errorMessage = String.format(
                    "File name \"%s\" can not be deleted", fullFileName);
            throw new DAOException(errorMessage, e);
        }
    }
}
