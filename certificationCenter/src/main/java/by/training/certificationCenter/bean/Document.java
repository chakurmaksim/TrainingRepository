package by.training.certificationCenter.bean;

import java.io.InputStream;

public class Document extends CertificationEntity {
    /**
     * Real path to the directory where the file is located.
     */
    private String uploadFilePath;
    /**
     * File name.
     */
    private String fileName;
    /**
     * Link to the InputStream.
     */
    private InputStream inputStream;
    /**
     * Link to the application to which this product belongs.
     */
    private Application application;

    public Document(final int newId) {
        super(newId);
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application newApplication) {
        this.application = newApplication;
    }
}
