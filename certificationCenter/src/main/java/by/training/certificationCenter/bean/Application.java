package by.training.certificationCenter.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Application extends CertificationEntity implements Serializable {
    /**
     * Registration number of the application.
     */
    private int reg_num;
    /**
     * Date when the application was added.
     */
    private LocalDate date_add;
    /**
     * Date when the application will be considered or executed.
     */
    private LocalDate date_resolve;
    /**
     * Technical requirements for product quality and safety.
     */
    private String requirements;
    /**
     * Application progress status.
     */
    private Status status;
    /**
     * Executor on the part of the client, who submitted the application.
     */
    private User executor;
    /**
     * Customer organisation.
     */
    private Organisation organisation;
    /**
     * Products that have to pass the certification procedure.
     */
    private List<Product> products;
    /**
     * List of documents that confirm the products conformity with
     * established requirements.
     */
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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Application that = (Application) o;
        return id == that.id
                && reg_num == that.reg_num
                && Objects.equals(date_add, that.date_add)
                && Objects.equals(date_resolve, that.date_resolve)
                && Objects.equals(requirements, that.requirements)
                && Objects.equals(executor, that.executor)
                && Objects.equals(products, that.products)
                && Objects.equals(documents, that.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reg_num, date_add, date_resolve, requirements,
                executor, products, documents);
    }

    @Override
    public String toString() {
        return "Application{"
                + ", id=" + id
                + "reg_num=" + reg_num
                + ", date_add=" + date_add
                + ", date_resolve=" + date_resolve +
                ", requirements='" + requirements + '\''
                + ", status=" + status
                + ", executor=" + executor
                + ", products=" + products
                + ", documents=" + documents
                + '}';
    }
}
