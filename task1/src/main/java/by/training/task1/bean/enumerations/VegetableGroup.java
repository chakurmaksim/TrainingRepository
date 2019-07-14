package by.training.task1.bean.enumerations;

/**
 * This enumeration describes the identifier and names of the group vegetables.
 */
public enum VegetableGroup {
    /**
     * Bean vegetable.
     */
    BEAN("bean vegetable"),
    /**
     * Bulbous vegetable.
     */
    BULBOUS("bulbous vegetable"),
    /**
     * Fruit vegetable.
     */
    FRUIT("fruit vegetable"),
    /**
     * Leafy vegetable.
     */
    LEAF("leafy vegetable"),
    /**
     * Root vegetable.
     */
    ROOT("root vegetable");

    /**
     * Declaration group name variable.
     */
    private String groupName;

    VegetableGroup(final String newGroupName) {
        this.groupName = newGroupName;
    }

    /**
     * Get method.
     *
     * @return name of the group
     */
    public String getGroupName() {
        return groupName;
    }
}
