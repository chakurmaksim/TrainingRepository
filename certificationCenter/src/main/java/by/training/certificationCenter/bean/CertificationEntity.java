package by.training.certificationCenter.bean;

import java.io.Serializable;

public abstract class CertificationEntity implements Serializable {
    protected int id;

    public CertificationEntity(final int newId) {
        this.id = newId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
