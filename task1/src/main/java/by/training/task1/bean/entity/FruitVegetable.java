package by.training.task1.bean.entity;

import by.training.task1.bean.enumerations.VegetableGroup;

/**
 * The class of vegetables that belong to the group of fruit vegetables.
 */
public class FruitVegetable extends Vegetable {
    /**
     * Vegetable hierarchy name.
     */
    private static final String VEGETABLE_GROUP_NAME;
    static {
        VEGETABLE_GROUP_NAME = VegetableGroup.FRUIT.getGroupName();
    }

    /**
     * Constructor with one parameter.
     *
     * @param newVegetableName vegetable name
     */
    public FruitVegetable(final String newVegetableName) {
        super(VEGETABLE_GROUP_NAME, newVegetableName);
    }
}
