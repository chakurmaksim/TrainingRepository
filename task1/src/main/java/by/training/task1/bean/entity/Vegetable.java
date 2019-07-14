package by.training.task1.bean.entity;

import java.util.Objects;

/**
 * Parent class of all types of vegetables.
 */
public abstract class Vegetable {
    /**
     * Vegetable hierarchy name.
     */
    private final String groupName;
    /**
     * Vegetable name.
     */
    private String vegName;
    /**
     * Amount of kilocalories per 100 g of the vegetables.
     */
    private int kcalPer100g;
    /**
     * Amount of proteins per 100 g of the vegetables.
     */
    private double proteinsPer100g;
    /**
     * Amount of fats per 100 g of the vegetables.
     */
    private double fatsPer100g;
    /**
     * Amount of carbohydrates per 100 g of the vegetables.
     */
    private double carbohydratesPer100g;

    /**
     * Constructor with two parameters.
     *
     * @param newGroupName vegetable group name
     * @param newVegName   vegetable name
     */
    public Vegetable(final String newGroupName, final String newVegName) {
        this.groupName = newGroupName;
        this.vegName = newVegName;
    }

    /**
     * Get method.
     *
     * @return vegetable group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Get method.
     *
     * @return vegetable name
     */
    public String getVegName() {
        return vegName;
    }

    /**
     * Method to set new value of instance variable of vegetable name.
     *
     * @param newVegName vegetable name
     */
    public void setVegName(final String newVegName) {
        this.vegName = newVegName;
    }

    /**
     * Get method.
     *
     * @return amount of kilocalories per 100 gram of vegetable
     */
    public int getKcalPer100g() {
        return kcalPer100g;
    }

    /**
     * Set method.
     *
     * @param newKcalPer100g kilocalories per 100 gram of vegetable
     */
    public void setKcalPer100g(final int newKcalPer100g) {
        this.kcalPer100g = newKcalPer100g;
    }

    /**
     * Get method.
     *
     * @return amount of proteins per 100 gram of vegetable with double format
     */
    public double getProteinsPer100g() {
        return proteinsPer100g;
    }

    /**
     * Set method.
     *
     * @param newProteinsPer100g proteins per 100 gram of vegetable
     */
    public void setProteinsPer100g(final double newProteinsPer100g) {
        this.proteinsPer100g = newProteinsPer100g;
    }

    /**
     * Get method.
     *
     * @return amount of fats per 100 gram of vegetable with double format
     */
    public double getFatsPer100g() {
        return fatsPer100g;
    }

    /**
     * Set method.
     *
     * @param newFatsPer100g fats per 100 gram of vegetable
     */
    public void setFatsPer100g(final double newFatsPer100g) {
        this.fatsPer100g = newFatsPer100g;
    }

    /**
     * Get method.
     *
     * @return amount of carbohydrates per 100 gram of vegetable
     */
    public double getCarbohydratesPer100g() {
        return carbohydratesPer100g;
    }

    /**
     * Set method.
     *
     * @param newCarbohydratesPer100g carbohydrates per 100 gram of vegetable
     */
    public void setCarbohydratesPer100g(final double newCarbohydratesPer100g) {
        this.carbohydratesPer100g = newCarbohydratesPer100g;
    }

    /**
     * Overridden equals method.
     *
     * @param o instance of the Vegetable class
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
        Vegetable vegetable = (Vegetable) o;
        return kcalPer100g == vegetable.kcalPer100g
                && Double.compare(vegetable.proteinsPer100g,
                proteinsPer100g) == 0
                && Double.compare(vegetable.fatsPer100g, fatsPer100g) == 0
                && Double.compare(vegetable.carbohydratesPer100g,
                carbohydratesPer100g) == 0
                && Objects.equals(groupName, vegetable.groupName)
                && Objects.equals(vegName, vegetable.vegName);
    }

    /**
     * Overridden hashCode method.
     * @return number with integer format
     */
    @Override
    public int hashCode() {
        return Objects.hash(groupName, vegName, kcalPer100g,
                proteinsPer100g, fatsPer100g, carbohydratesPer100g);
    }

    /**
     * Overridden toString method.
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "Vegetable{"
                + "groupName='" + groupName + '\''
                + ", vegName='" + vegName + '\''
                + ", kcalPer100g=" + kcalPer100g
                + ", proteinsPer100g=" + proteinsPer100g
                + ", fatsPer100g=" + fatsPer100g
                + ", carbohydratesPer100g=" + carbohydratesPer100g
                + '}';
    }
}
