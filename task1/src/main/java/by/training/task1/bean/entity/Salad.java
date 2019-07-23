package by.training.task1.bean.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class contains information about the calories of salad
 * and about vegetables are in the composition.
 */
public class Salad {
    /**
     * Order number (salad id).
     */
    private long saladID;
    /**
     * Date of making salad.
     */
    private String date;
    /**
     * Name of the particular salad.
     */
    private String name;
    /**
     * Map of ingredients that the salad contains.
     */
    private Map<Vegetable, Integer> ingredients;
    /**
     * Total weight of the salad.
     */
    private int weight;
    /**
     * Total number of calories of this salad.
     */
    private double kcal;
    /**
     * Amount of kilocalories per 100 g of salad.
     */
    private double kcalPer100g;

    /**
     * Constructor with one parameter.
     *
     * @param newName salad name
     */
    public Salad(final String newName) {
        this.name = newName;
        ingredients = new HashMap<>();
    }

    /**
     * Get method.
     *
     * @return salad order number or salad ID
     */
    public long getSaladID() {
        return saladID;
    }

    /**
     * Set method.
     *
     * @param newSaladID salad order number or salad ID
     */
    public void setSaladID(final long newSaladID) {
        this.saladID = newSaladID;
    }

    /**
     * Get method.
     *
     * @return date of manufacture
     */
    public String getDate() {
        return date;
    }

    /**
     * Set method.
     *
     * @param newDate date of manufacture
     */
    public void setDate(final String newDate) {
        this.date = newDate;
    }

    /**
     * Get method.
     *
     * @return salad name
     */
    public String getName() {
        return name;
    }

    /**
     * Set method.
     *
     * @param newName salad name
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * Get method.
     *
     * @return map of the salad ingredients
     */
    public Map<Vegetable, Integer> getIngredients() {
        return ingredients;
    }

    /**
     * Set method.
     *
     * @param newIngredients map of the salad ingredients
     */
    public void setIngredients(final Map<Vegetable, Integer> newIngredients) {
        this.ingredients = newIngredients;
    }

    /**
     * Get method.
     *
     * @return general weight of the salad
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set method.
     *
     * @param newWeight general weight of the salad
     */
    public void setWeight(final int newWeight) {
        this.weight = newWeight;
    }

    /**
     * Get method.
     *
     * @return total amount of kilocalories
     */
    public double getKcal() {
        return kcal;
    }

    /**
     * Set method.
     *
     * @param newKcal total amount of kilocalories
     */
    public void setKcal(final double newKcal) {
        this.kcal = newKcal;
    }

    /**
     * Get method.
     *
     * @return amount of kilocalories per 100 gram of salad
     */
    public double getKcalPer100g() {
        return kcalPer100g;
    }

    /**
     * Set method.
     *
     * @param newKcalPer100g amount of kilocalories per 100 gram of salad
     */
    public void setKcalPer100g(final double newKcalPer100g) {
        this.kcalPer100g = newKcalPer100g;
    }

    /**
     * Overridden equals method.
     *
     * @param o instance of the Salad class
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
        Salad salad = (Salad) o;
        return saladID == salad.saladID
                && weight == salad.weight
                && Double.compare(salad.kcal, kcal) == 0
                && Double.compare(salad.kcalPer100g, kcalPer100g) == 0
                && Objects.equals(date, salad.date)
                && Objects.equals(name, salad.name)
                && Objects.equals(ingredients, salad.ingredients);
    }

    /**
     * Overridden hashCode method.
     *
     * @return number with integer format
     */
    @Override
    public int hashCode() {
        return Objects.hash(saladID, date, name,
                ingredients, weight, kcal, kcalPer100g);
    }

    /**
     * Overridden toString method.
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "Salad{"
                + "saladID=" + saladID
                + ", date='" + date + '\''
                + ", name='" + name + '\''
                + ", weight=" + weight
                + ", kcal=" + kcal
                + ", kcalPer100g=" + kcalPer100g
                + '}';
    }
}
