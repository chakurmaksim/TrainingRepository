package by.training.certificationCenter.bean;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificationEntity entity = (CertificationEntity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
