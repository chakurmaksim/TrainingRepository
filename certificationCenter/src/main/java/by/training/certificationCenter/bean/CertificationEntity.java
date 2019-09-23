package by.training.certificationCenter.bean;

import java.io.Serializable;

public abstract class CertificationEntity implements Serializable {
    protected final int id;
    public CertificationEntity(final int newId) {
        this.id = newId;
    }

    public int getId() {
        return id;
    }
}
