package by.training.task1.bean.entity;

import by.training.task1.bean.enumerations.VegetableGroup;

/**
 * The class of vegetables that belong to the group of bulbous vegetables.
 */
public class BulbousVegetable extends Vegetable {
    /**
     * Vegetable hierarchy name.
     */
    private static final String VEGETABLE_GROUP_NAME;

    static {
        VEGETABLE_GROUP_NAME = VegetableGroup.BULBOUS.getGroupName();
    }

    /**
     * Constructor with one parameter.
     *
     * @param newVegetableName vegetable name
     */
    public BulbousVegetable(final String newVegetableName) {
        super(VEGETABLE_GROUP_NAME, newVegetableName);
    }
}
