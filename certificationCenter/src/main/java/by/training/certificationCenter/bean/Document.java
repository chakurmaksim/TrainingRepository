package by.training.certificationCenter.bean;

public class Document {
    private final int id;
    private String path;

    public Document(final int newId) {
        this.id = newId;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
