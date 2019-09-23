package by.training.certificationCenter.bean;

import java.time.LocalDate;
import java.util.List;

public class Application extends CertificationEntity {
    private int reg_num;
    private LocalDate date_add;
    private LocalDate date_resolve;
    private String requirements;
    private Status status;
    private User executor;
    private Organisation org;
    private List<Product> products;
    private List<Document> documents;

    public Application(final int newId) {
        super(newId);
    }

    public int getReg_num() {
        return reg_num;
    }

    public void setReg_num(int reg_num) {
        this.reg_num = reg_num;
    }

    public LocalDate getDate_add() {
        return date_add;
    }

    public void setDate_add(LocalDate date_add) {
        this.date_add = date_add;
    }

    public LocalDate getDate_resolve() {
        return date_resolve;
    }

    public void setDate_resolve(LocalDate date_resolve) {
        this.date_resolve = date_resolve;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public Organisation getOrg() {
        return org;
    }

    public void setOrg(Organisation org) {
        this.org = org;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "Application{"
                + "reg_num=" + reg_num
                + ", date_add=" + date_add
                + ", date_resolve=" + date_resolve
                + ", requirements='" + requirements + '\''
                + ", status=" + status
                + ", executor=" + executor
                + ", org=" + org
                + ", products=" + products
                + ", documents=" + documents
                + '}';
    }
}
