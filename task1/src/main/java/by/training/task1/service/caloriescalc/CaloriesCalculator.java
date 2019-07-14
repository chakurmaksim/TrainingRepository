package by.training.task1.service.caloriescalc;

/**
 * Service class for calculating calories.
 */
public final class CaloriesCalculator {
    /**
     * The energy released during the oxidation of 1 gram of protein.
     */
    private static final int PROTEIN_ENERGY_VALUE = 4;
    /**
     * The energy released during the oxidation of 1 gram of fat.
     */
    private static final int FAT_ENERGY_VALUE = 9;
    /**
     * The energy released during the oxidation of 1 gram of carbohydrate.
     */
    private static final int CARBOHYDRATE_ENERGY_VALUE = 4;
    /**
     * Weight constant in grams.
     */
    private static final double WEIGHT_100 = 100.0;
    private CaloriesCalculator() {
    }

    /**
     * Method to calculate calories using nutrients: proteins,
     * fats and carbohydrates.
     * @param proteins proteins
     * @param fats fats
     * @param carbohydrates carbohydrates
     * @return kilocalories in integer format
     */
    public static int calcCaloriesUseNutrients(final double proteins,
                                               final double fats,
                                               final double carbohydrates) {
        return (int) (proteins * PROTEIN_ENERGY_VALUE + fats * FAT_ENERGY_VALUE
                + carbohydrates * CARBOHYDRATE_ENERGY_VALUE);
    }

    /**
     * Count a general amount of kilocalories at particular salad.
     * @param currentVal current value kilocalories
     * @param prodKcal number of kilocalories in the added ingredient
     * @param prodWeight ingredient weight
     * @return total calories dishes
     */
    public static double calcGeneralNumCalories(final double currentVal,
                                                final int prodKcal,
                                                final int prodWeight) {
        return currentVal + prodKcal * prodWeight / WEIGHT_100;
    }

    /**
     * Calculate the number of kilocalories per 100 g of salad.
     * @param generalKcal total quantity of kilocalories
     * @param generalWeight total dish weight
     * @return number of kilocalories per 100 g of salad
     */
    public static double calcNumCaloriesPer100g(final double generalKcal,
                                                final double generalWeight) {
        return generalKcal * WEIGHT_100 / generalWeight;
    }
}
