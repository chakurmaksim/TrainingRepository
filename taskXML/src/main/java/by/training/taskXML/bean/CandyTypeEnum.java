package by.training.taskXML.bean;

public enum CandyTypeEnum {
    /**
     * Element name means the candie type.
     */
    CARAMEL("caramel"),
    /**
     * Element name means the candie type.
     */
    CHOCOLATE("chocolate"),
    /**
     * Element name means the candie type.
     */
    IRIS("iris"),
    /**
     * Attribute name of the candy type element.
     */
    ISWRAPPED("isWrapped"),
    /**
     * Attribute name of the candy type element.
     */
    ISGLAZED("isGlazed"),
    /**
     * Element name of the chocolate type.
     */
    GLAZE("glaze"),
    /**
     * Element name of the chocolate type.
     */
    BODY("body"),
    /**
     * Element name of the caramel type.
     */
    GRADE("grade"),
    /**
     * Element name of the caramel type.
     */
    PROCESSINGMETHOD("processingMethod"),
    /**
     * Element name of the iris type.
     */
    PRODUCTIONMETHOD("productionMethod"),
    /**
     * Element name of the iris type.
     */
    STRUCTURE("structure");
    /**
     * Description.
     */
    private String value;

    CandyTypeEnum(final String newValue) {
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
