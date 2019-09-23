package by.training.certificationCenter.bean;

public class QuantityAttribute {
    private final byte id;
    private String attribute;

    public QuantityAttribute(final byte newId, final String attr) {
        this.id = newId;
        this.attribute = attr;
    }

    public byte getId() {
        return id;
    }

    public String getAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return "Количественный атрибут{"
                + "индекс=" + id +
                ", признак='" + attribute + '\'' +
                '}';
    }
}
