package by.training.certificationCenter.service.factory;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Document;

import java.io.InputStream;
import java.io.Serializable;

public final class DocumentFactory implements Cloneable, Serializable {
    /**
     * Variable for keeping DocumentFactory instance.
     */
    private static final DocumentFactory SINGLE_INSTANCE;
    static {
        SINGLE_INSTANCE = new DocumentFactory();
    }
    private DocumentFactory() {
    }

    public static DocumentFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    public Document createNewDocument(
            final String uploadFilePath, final String fileName,
            final InputStream inputStream, final Application application) {
        Document document = new Document(0);
        document.setUploadFilePath(uploadFilePath);
        document.setFileName(fileName);
        document.setInputStream(inputStream);
        document.setApplication(application);
        return document;
    }

    public Document createExistedDocument(
            final int id, final String filePathName) {
        Document document = new Document(id);
        int slashLastIndex = filePathName.lastIndexOf("/");
        String uploadFilePath = filePathName.substring(0, slashLastIndex + 1);
        String fileName = filePathName.substring(slashLastIndex + 1);
        document.setUploadFilePath(uploadFilePath);
        document.setFileName(fileName);
        return document;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return SINGLE_INSTANCE;
    }

    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }

}
