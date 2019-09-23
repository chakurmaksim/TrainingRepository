package by.training.certificationCenter.bean;

public class Product extends CertificationEntity {
    private String name;
    private long code;
    private String producer;
    private String address;
    private QuantityAttribute attr;

    public Product(final int newId) {
        super(newId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public QuantityAttribute getAttr() {
        return attr;
    }

    public void setAttr(QuantityAttribute attr) {
        this.attr = attr;
    }

    @Override
    public String toString() {
        return "Продукция{"
                + "Наименование и обозначение='" + name + '\''
                + ", Код ТН ВЭД ЕАЭС=" + code
                + ", Изготовитель='" + producer + '\''
                + ", Место нахождения='" + address + '\''
                + ", Признак выпуска=" + attr
                + '}';
    }
}
