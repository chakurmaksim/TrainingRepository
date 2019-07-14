package by.training.task1.service.validator;

import by.training.task1.bean.enumerations.VegetableGroup;

/**
 * Class validator to check the input parameters of the vegetable object.
 */
public class VegetableValidator {
    /**
     * Method to check such vegetable group name exists.
     *
     * @param groupName vegetable group name
     * @return true if such group exists or false if does not
     */
    public boolean validateGroupName(final String groupName) {
        for (VegetableGroup group : VegetableGroup.values()) {
            if (group.getGroupName().equals(groupName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check vegetable name is empty.
     *
     * @param vegName vegetable name
     * @return true if name is empty
     */
    public boolean validateIsNameEmpty(final String vegName) {
        return "".equals(vegName.trim());
    }

    /**
     * Method to check vegetable calories.
     *
     * @param kcalPer100g kilocalories per 100 gram of vegetable
     * @return true if negative value
     */
    public boolean validateNegCaloriesNum(final int kcalPer100g) {
        return kcalPer100g < 0;
    }

    /**
     * Method to check vegetable proteins content.
     *
     * @param nutrients protein per 100 gram of vegetable
     * @return true if negative value
     */
    public boolean validateNegNutrientNum(final double nutrients) {
        return nutrients < 0.0;
    }
}
