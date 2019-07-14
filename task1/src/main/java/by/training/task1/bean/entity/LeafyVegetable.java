package by.training.task1.bean.entity;

import by.training.task1.bean.enumerations.VegetableGroup;

/**
 * The class of vegetables that belong to the group of leafy vegetables.
 */
public class LeafyVegetable extends Vegetable {
    /**
     * Vegetable hierarchy name.
     */
    private static final String VEGETABLE_GROUP_NAME;

    static {
        VEGETABLE_GROUP_NAME = VegetableGroup.LEAF.getGroupName();
    }

    /**
     * Constructor with one parameter.
     *
     * @param newVegetableName vegetable name
     */
    public LeafyVegetable(final String newVegetableName) {
        super(VEGETABLE_GROUP_NAME, newVegetableName);
    }
}
