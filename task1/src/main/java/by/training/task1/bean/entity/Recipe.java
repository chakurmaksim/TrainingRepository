package by.training.task1.bean.entity;

import java.util.Map;
import java.util.Objects;

/**
 * Class contains the salad name, composition and its quantity.
 */
public class Recipe {
    /**
     * Name of a specific salad.
     */
    private String dishName;
    /**
     * Names of ingredients and its amount that the salad contains.
     */
    private Map<String, Integer> composition;

    /**
     * Constructor with two parameters.
     *
     * @param newDishName    name of the recipe and salad respectively
     * @param newComposition specific ingredients
     */
    public Recipe(final String newDishName,
                  final Map<String, Integer> newComposition) {
        this.dishName = newDishName;
        this.composition = newComposition;
    }

    /**
     * Get method.
     *
     * @return salad name
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * Set method.
     *
     * @param newDishName salad name
     */
    public void setDishName(final String newDishName) {
        this.dishName = newDishName;
    }

    /**
     * Get method.
     *
     * @return map of salad composition
     */
    public Map<String, Integer> getComposition() {
        return composition;
    }

    /**
     * Set method.
     *
     * @param newComposition map of salad composition
     */
    public void setComposition(final Map<String, Integer> newComposition) {
        this.composition = newComposition;
    }

    /**
     * Overridden equals method.
     *
     * @param o instance of the Recipe class
     * @return true if this and another object are equals
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return Objects.equals(dishName, recipe.dishName)
                && Objects.equals(composition, recipe.composition);
    }

    /**
     * Overridden hashCode method.
     *
     * @return number with integer format
     */
    @Override
    public int hashCode() {
        return Objects.hash(dishName, composition);
    }

    /**
     * Overridden toString method.
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "Recipe{"
                + "dishName='" + dishName + '\''
                + ", composition=" + composition
                + '}';
    }
}
