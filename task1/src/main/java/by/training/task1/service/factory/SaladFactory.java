package by.training.task1.service.factory;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.entity.Salad;
import by.training.task1.bean.entity.Vegetable;
import by.training.task1.bean.exception.RecipeSyntaxException;
import by.training.task1.service.timestamp.TimeStamp;

import java.util.List;
import java.util.Map;

import static by.training.task1.service.caloriescalc.CaloriesCalculator.calcGeneralNumCalories;
import static by.training.task1.service.caloriescalc.CaloriesCalculator.calcNumCaloriesPer100g;

/**
 * Class for creating an object of Salad class.
 */
public final class SaladFactory {
    /**
     * Variable for keeping SaladFactory instance.
     */
    private static final SaladFactory SINGLE_INSTANCE;
    /**
     * Order number.
     */
    private static long orderID = 0;

    static {
        SINGLE_INSTANCE = new SaladFactory();
    }
    private SaladFactory() {
    }

    /**
     * Method to create a salad from a recipe and a set of vegetables.
     * @param recipe particular recipe
     * @param vegetableSet set of vegetables
     * @return object of Salad class
     * @throws RecipeSyntaxException when the names of vegetables on the recipe
     * and in the set do not match
     */
    public Salad makeSalad(final Recipe recipe,
                           final List<Vegetable> vegetableSet)
            throws RecipeSyntaxException {
        Salad salad = new Salad(recipe.getDishName());
        Map<String, Integer> composition = recipe.getComposition();
        for (Map.Entry<String, Integer> entry : composition.entrySet()) {
            for (Vegetable vegetable : vegetableSet) {
                if (entry.getKey().equalsIgnoreCase(vegetable.getVegName())) {
                    salad.getIngredients().put(vegetable, entry.getValue());
                    salad.setWeight(salad.getWeight() + entry.getValue());
                    double kcal = calcGeneralNumCalories(salad.getKcal(),
                            vegetable.getKcalPer100g(), entry.getValue());
                    salad.setKcal(kcal);
                }
            }
        }
        if (salad.getIngredients().size() != recipe.getComposition().size()) {
            String message = String.format("No names matches "
                    + "in vegetable and recipe lists. Recipe name: %s",
                    recipe.getDishName());
            throw new RecipeSyntaxException(message);
        }
        salad.setKcalPer100g(calcNumCaloriesPer100g(salad.getKcal(),
                salad.getWeight()));
        salad.setDate(TimeStamp.getTimeStamp());
        salad.setSaladID(++orderID);
        return salad;
    }

    /**
     * Get method.
     *
     * @return single instance SaladFactory
     */
    public static SaladFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
