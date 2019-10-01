package by.training.certificationCenter.bean;

public class QuantityAttribute {
    private final int id;
    private String attributeName;

    public QuantityAttribute(final int newId, final String attrName) {
        this.id = newId;
        this.attributeName = attrName;
    }

    public int getId() {
        return id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String toString() {
        return "Количественный признак{"
                + "индекс=" + id
                + ", признак='" + attributeName + '\''
                + '}';
    }
    public enum AttributeName {
        SERIAL("Серийный выпуск"),
        BATCH("Партия"),
        SINGLE("Единичное изделие");

        private String description;
        AttributeName(final String newDescription) {
            this.description = newDescription;
        }

        public static AttributeName getByIdentity(final int identity) {
            return AttributeName.values()[identity - 1];
        }

        public String getDescription() {
            return description;
        }
    }
}
