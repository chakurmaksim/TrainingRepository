package by.training.task1.service.caloriescalc;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CaloriesCalculatorTest {

    @Test(description = "Validate calculation kcal depending on nutrients")
    public void testCalcCaloriesUseNutrients() {
        double proteins = 2.0;
        int proteinEnergyVal = 4;
        double fats = 0.8;
        int fatsEnergyVal = 9;
        double carbohydrates = 3.7;
        int carbohydratesEnergyVal = 4;
        int expected = (int) (proteins * proteinEnergyVal + fats * fatsEnergyVal
                + carbohydrates * carbohydratesEnergyVal);
        int actual = CaloriesCalculator.calcCaloriesUseNutrients(
                proteins, fats, carbohydrates);
        assertEquals(actual, expected);
    }

    @Test(description = "Validate calculation the total number of kcal")
    public void testCalcGeneralNumCalories() {
        double currentKcal = 15;
        int productKcal = 20;
        int productWeight = 190;
        double expected = currentKcal + productKcal * productWeight / 100;
        double actual = CaloriesCalculator.calcGeneralNumCalories(
                currentKcal, productKcal, productWeight);
        assertEquals(actual, expected);
    }

    @Test(description = "Validate calculation kcal per 100 gram")
    public void testCalcNumCaloriesPer100g() {
        double totalKcal = 35;
        double totalWeight = 200;
        double expected = totalKcal * 100 / totalWeight;
        double actual = CaloriesCalculator.calcNumCaloriesPer100g(
                totalKcal, totalWeight);
        assertEquals(actual, expected);
    }
}