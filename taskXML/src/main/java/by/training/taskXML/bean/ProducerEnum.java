package by.training.taskXML.bean;

public enum ProducerEnum {
    /**
     * Element name for the producer.
     */
    PRODUCER("producer"),
    /**
     * Producer name.
     */
    PRODUCERNAME("producerName"),
    /**
     * Address of the producer.
     */
    ADDRESS("address"),
    /**
     * Element of country.
     */
    COUNTRY("country"),
    /**
     * Element of postcode.
     */
    POSTCODE("postcode"),
    /**
     * Element of region.
     */
    REGION("region"),
    /**
     * Element of district.
     */
    DISTRICT("district"),
    /**
     * Element of city.
     */
    CITY("city"),
    /**
     * Element of village.
     */
    VILLAGE("village"),
    /**
     * Element of street.
     */
    STREET("street"),
    /**
     * Element of a building.
     */
    BUILDING("building"),
    /**
     * Corps of the building.
     */
    CORPS("corps");
    /**
     * Description.
     */
    private String value;

    ProducerEnum(final String newValue) {
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
