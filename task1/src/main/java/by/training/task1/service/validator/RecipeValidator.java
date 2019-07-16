package by.training.task1.service.validator;

import java.util.Map;

/**
 * Class validator to check the input parameters of the Recipe object.
 */
public class RecipeValidator {
    /**
     * Method to check recipe name is empty.
     *
     * @param recipeName vegetable name
     * @return true if name is empty
     */
    public boolean validateIsRecipeNameEmpty(final String recipeName) {
        return recipeName != null ? "".equals(recipeName.trim()) : true;
    }

    /**
     * Method to check recipe name is empty.
     *
     * @param composition salad ingredients
     * @return true if one of the salad ingredients name is empty
     */
    public boolean validateIsIngrNameEmpty(final Map<String,
            Integer> composition) {
        if (composition == null) {
            return true;
        }
        for (Map.Entry<String, Integer> entry : composition.entrySet()) {
            if ("".equals(entry.getKey().trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check value of each salad ingridient is less than zero.
     *
     * @param composition salad ingredients
     * @return true if one of the salad ingredients is negative value
     */
    public boolean validateNegIngrNum(final Map<String, Integer> composition) {
        if (composition == null) {
            return true;
        }
        for (Map.Entry<String, Integer> entry : composition.entrySet()) {
            if (entry.getValue() < 0) {
                return true;
            }
        }
        return false;
    }
}
