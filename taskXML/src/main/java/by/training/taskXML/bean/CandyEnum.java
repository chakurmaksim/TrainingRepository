package by.training.taskXML.bean;

public enum CandyEnum {
    /**
     * Name of the root element.
     */
    CANDIES("candies"),
    /**
     * Element name.
     */
    CANDY("candy"),
    /**
     * Attribute name.
     */
    BARCODE("barcode"),
    /**
     * Element name means the candie name.
     */
    CANDYNAME("candyName"),
    /**
     * Element name for the candie composition.
     */
    COMPOSITION("composition"),
    /**
     * Element name for manufacture date of the candie.
     */
    MANUFACTUREDATE("manufactureDate"),
    /**
     * Element name for shelf life of the candie.
     */
    SHELFLIFE("shelfLife"),
    /**
     * Element name.
     */
    PROTEINS("proteins"),
    /**
     * Element name.
     */
    FATS("fats"),
    /**
     * Element name.
     */
    CARBOHYDRATES("carbohydrates"),
    /**
     * Element name.
     */
    ENERGY("energy"),
    /**
     * Element name for the nutritional value.
     */
    NUTRITIONALVALUE("nutritionalValue"),
    /**
     * Unit of measurement for the shelf life.
     */
    MEASURE("measure");
    /**
     * Description.
     */
    private String value;

    /**
     * Assign a new description.
     *
     * @param newValue description
     */
    CandyEnum(final String newValue) {
        this.value = newValue;
    }

    /**
     * Get method.
     *
     * @return description
     */
    public String getValue() {
        return value;
    }
}
