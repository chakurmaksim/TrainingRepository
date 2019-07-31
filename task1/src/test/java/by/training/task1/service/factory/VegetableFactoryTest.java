package by.training.task1.service.factory;

import by.training.task1.bean.entity.*;
import by.training.task1.bean.exception.NoSuchIngredientException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class VegetableFactoryTest {
    VegetableFactory vegetableFactory = VegetableFactory.getSingleInstance();

    @DataProvider(name = "positiveDataForCreateFromJson")
    public Object[][] createPositiveDataFor_CreateVegFromJson() {
        String rawBeanVeg = "{\"groupName\":\"bean vegetable\",\"vegName\":\"green peas\","
                + "\"kcalPer100g\":73,\"proteinsPer100g\":5.0,\"fatsPer100g\":0.2,"
                + "\"carbohydratesPer100g\":13.8}";
        String rawBulbousVeg = "{\"groupName\":\"bulbous vegetable\",\"vegName\":\"garlic\","
                + "\"kcalPer100g\":143,\"proteinsPer100g\":6.5,\"fatsPer100g\":0.5,"
                + "\"carbohydratesPer100g\":29.9}";
        String rawFruitVeg = "{\"groupName\":\"fruit vegetable\",\"vegName\":\"tomato\","
                + "\"kcalPer100g\":20,\"proteinsPer100g\":1.1,\"fatsPer100g\":0.2,"
                + "\"carbohydratesPer100g\":3.7}";
        String rawLeafyVeg = "{\"groupName\":\"leafy vegetable\",\"vegName\":\"spinach\","
                + "\"kcalPer100g\":22,\"proteinsPer100g\":2.9,\"fatsPer100g\":0.3,"
                + "\"carbohydratesPer100g\":2.0}";
        String rawRootVeg = "{\"groupName\":\"root vegetable\",\"vegName\":\"potato\","
                + "\"kcalPer100g\":76,\"proteinsPer100g\":2.0,\"fatsPer100g\":0.4,"
                + "\"carbohydratesPer100g\":16.1}";
        String rawFruitVegWithNegKcal = "{\"groupName\":\"fruit vegetable\",\"vegName\":\"cucumber\","
                + "\"kcalPer100g\":-15,\"proteinsPer100g\":0.8,\"fatsPer100g\":0.1,"
                + "\"carbohydratesPer100g\":2.8}";
        Vegetable[] vegArr = new Vegetable[]{
                new BeanVegetable("green peas"),
                new BulbousVegetable("garlic"),
                new FruitVegetable("tomato"),
                new LeafyVegetable("spinach"),
                new RootVegetable("potato"),
        };
        vegArr[0].setKcalPer100g(73);
        vegArr[0].setProteinsPer100g(5.0);
        vegArr[0].setFatsPer100g(0.2);
        vegArr[0].setCarbohydratesPer100g(13.8);
        vegArr[1].setKcalPer100g(143);
        vegArr[1].setProteinsPer100g(6.5);
        vegArr[1].setFatsPer100g(0.5);
        vegArr[1].setCarbohydratesPer100g(29.9);
        vegArr[2].setKcalPer100g(20);
        vegArr[2].setProteinsPer100g(1.1);
        vegArr[2].setFatsPer100g(0.2);
        vegArr[2].setCarbohydratesPer100g(3.7);
        vegArr[3].setKcalPer100g(22);
        vegArr[3].setProteinsPer100g(2.9);
        vegArr[3].setFatsPer100g(0.3);
        vegArr[3].setCarbohydratesPer100g(2.0);
        vegArr[4].setKcalPer100g(76);
        vegArr[4].setProteinsPer100g(2.0);
        vegArr[4].setFatsPer100g(0.4);
        vegArr[4].setCarbohydratesPer100g(16.1);
        FruitVegetable fruitVegWithNegKcal = new FruitVegetable("cucumber");
        fruitVegWithNegKcal.setKcalPer100g((int) (0.8 * 4 + 0.1 * 9 + 2.8 * 4));
        fruitVegWithNegKcal.setProteinsPer100g(0.8);
        fruitVegWithNegKcal.setFatsPer100g(0.1);
        fruitVegWithNegKcal.setCarbohydratesPer100g(2.8);
        return new Object[][]{
                {rawBeanVeg, vegArr[0]},
                {rawBulbousVeg, vegArr[1]},
                {rawFruitVeg, vegArr[2]},
                {rawLeafyVeg, vegArr[3]},
                {rawRootVeg, vegArr[4]},
                {rawFruitVegWithNegKcal, fruitVegWithNegKcal}
        };
    }

    @DataProvider(name = "negativeDataForCreateFromJson")
    public Object[] createNegativeDataFor_CreateVegFromJson() {
        String raw1 = "{\" \":\"fruit vegetable\",\"vegName\":\"red pepper\","
                + "\"kcalPer100g\":27,\"proteinsPer100g\":1.3,\"fatsPer100g\":0.0,"
                + "\"carbohydratesPer100g\":5.3}";
        String raw2 = "{\"groupName\":\" \",\"vegName\":\"cucumber\","
                + "\"kcalPer100g\":15,\"proteinsPer100g\":0.8,\"fatsPer100g\":0.1,"
                + "\"carbohydratesPer100g\":2.8}";
        String raw3 = "{\"groupName\":\"fruit vegetable\",\" \":\"tomato\","
                + "\"kcalPer100g\":20,\"proteinsPer100g\":1.1,\"fatsPer100g\":0.2,"
                + "\"carbohydratesPer100g\":3.7}";
        String raw4 = "{\"groupName\":\"fruit vegetable\",\"vegName\":\" \","
                + "\"kcalPer100g\":24,\"proteinsPer100g\":1.2,\"fatsPer100g\":0.1,"
                + "\"carbohydratesPer100g\":4.5}";
        String raw5 = "{\"groupName\":\"fruit vegetable\",\"vegName\":\"zucchini\","
                + "\"kcalPer100g\":-24,\"proteinsPer100g\":-0.6,\"fatsPer100g\":0.3,"
                + "\"carbohydratesPer100g\":4.6}";
        String raw6 = "{:\"bulbous vegetable\",\"vegName\":\"onion\","
                + "\"kcalPer100g\":47,\"proteinsPer100g\":1.4,\"fatsPer100g\":0.0,"
                + "\"carbohydratesPer100g\":10.4}";
        String raw7 = "{\"groupName\":,\"vegName\":\"garlic\",\"kcalPer100g\":143,"
                + "\"proteinsPer100g\":6.5,\"fatsPer100g\":0.5,"
                + "\"carbohydratesPer100g\":29.9}";
        String raw8 = "{\"groupName\":\"leafy vegetable\",:\"arugula\","
                + "\"kcalPer100g\":25,\"proteinsPer100g\":2.6,\"fatsPer100g\":0.7,"
                + "\"carbohydratesPer100g\":2.1}";
        String raw9 = "{\"groupName\":\"leafy vegetable\",\"vegName\":,"
                + "\"kcalPer100g\":27,\"proteinsPer100g\":1.8,\"fatsPer100g\":0.1,"
                + "\"carbohydratesPer100g\":4.7}";
        String raw10 = "{\"groupName\":\"leafy vegetable\",\"vegName\":\"dill\","
                + "\"kcalPer100g\":,\"proteinsPer100g\":2.5,\"fatsPer100g\":0.5,"
                + "\"carbohydratesPer100g\":6.3}";
        return new Object[]{raw1, raw2, raw3, raw4, raw5, raw6, raw7, raw8, raw9, raw10};
    }

    @Test(description = "Positive scenario of the creating vegetable",
            dataProvider = "positiveDataForCreateFromJson")
    public void testCreateVegFromJson(String rawVeg, Vegetable expected) {
        Vegetable actual = vegetableFactory.createVegFromJson(rawVeg);
        assertEquals(actual, expected);
    }

    @Test(description = "Negative scenario of the creating vegetable",
            dataProvider = "negativeDataForCreateFromJson")
    public void negativeDataTestCreateVegFromJson(String rawVeg) {
        assertThrows(() -> vegetableFactory.createVegFromJson(rawVeg));
    }

    @Test(description = "Confirm exceptions thrown when creating a vegetable",
            dataProvider = "negativeDataForCreateFromJson",
            expectedExceptions = NoSuchIngredientException.class)
    public void testCreateVegFromJson_CheckThrownException(String raw)
            throws NoSuchIngredientException {
        vegetableFactory.createVegFromJson(raw);
    }
}