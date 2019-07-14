package by.training.task1.service.factory;

import by.training.task1.bean.entity.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

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
        BeanVegetable beanVegetable = new BeanVegetable("green peas");
        beanVegetable.setKcalPer100g(73);
        beanVegetable.setProteinsPer100g(5.0);
        beanVegetable.setFatsPer100g(0.2);
        beanVegetable.setCarbohydratesPer100g(13.8);
        BulbousVegetable bulbousVegetable = new BulbousVegetable("garlic");
        bulbousVegetable.setKcalPer100g(143);
        bulbousVegetable.setProteinsPer100g(6.5);
        bulbousVegetable.setFatsPer100g(0.5);
        bulbousVegetable.setCarbohydratesPer100g(29.9);
        FruitVegetable fruitVegetable = new FruitVegetable("tomato");
        fruitVegetable.setKcalPer100g(20);
        fruitVegetable.setProteinsPer100g(1.1);
        fruitVegetable.setFatsPer100g(0.2);
        fruitVegetable.setCarbohydratesPer100g(3.7);
        LeafyVegetable leafyVegetable = new LeafyVegetable("spinach");
        leafyVegetable.setKcalPer100g(22);
        leafyVegetable.setProteinsPer100g(2.9);
        leafyVegetable.setFatsPer100g(0.3);
        leafyVegetable.setCarbohydratesPer100g(2.0);
        RootVegetable rootVegetable = new RootVegetable("potato");
        rootVegetable.setKcalPer100g(76);
        rootVegetable.setProteinsPer100g(2.0);
        rootVegetable.setFatsPer100g(0.4);
        rootVegetable.setCarbohydratesPer100g(16.1);
        FruitVegetable fruitVegWithNegKcal = new FruitVegetable("cucumber");
        fruitVegWithNegKcal.setKcalPer100g((int) (0.8 * 4 + 0.1 * 9 + 2.8 * 4));
        fruitVegWithNegKcal.setProteinsPer100g(0.8);
        fruitVegWithNegKcal.setFatsPer100g(0.1);
        fruitVegWithNegKcal.setCarbohydratesPer100g(2.8);

        return new Object[][]{
                {rawBeanVeg, beanVegetable},
                {rawBulbousVeg, bulbousVegetable},
                {rawFruitVeg, fruitVegetable},
                {rawLeafyVeg, leafyVegetable},
                {rawRootVeg, rootVegetable},
                {rawFruitVegWithNegKcal, fruitVegWithNegKcal}
        };
    }

    @DataProvider(name = "negativeDataForCreateFromJson")
    public Object[] createNegativeDataFor_CreateVegFromJson() {
        String rawBulbousVeg = "{\"groupName\":\"bulbous vegetable\",\"vegName\":\" \","
                + "\"kcalPer100g\":47,\"proteinsPer100g\":1.4,\"fatsPer100g\":0.0,"
                + "\"carbohydratesPer100g\":10.4}";
        String rawFruitVeg = "{\"groupName\":\"fruit vegetable\",\"vegName\":\"sweet red pepper\","
                + "\"kcalPer100g\":-27,\"proteinsPer100g\":-1.3,\"fatsPer100g\":0.0,"
                + "\"carbohydratesPer100g\":5.3}";
        String rawLeafyVeg = "{\"groupName\":,\"vegName\":\"parsley\","
                + "\"kcalPer100g\":47,\"proteinsPer100g\":3.7,\"fatsPer100g\":0.4,"
                + "\"carbohydratesPer100g\":7.6}";
        String rawRootVeg = "{\"groupName\":\"vegetable\",\"vegName\":\"carrot\","
                + "\"kcalPer100g\":32,\"proteinsPer100g\":1.3,\"fatsPer100g\":0.1,"
                + "\"carbohydratesPer100g\":6.9}";

        return new Object[]{rawBulbousVeg, rawFruitVeg, rawLeafyVeg, rawRootVeg};
    }

    @Test(description = "Positive scenario of the creating vegetable", dataProvider = "positiveDataForCreateFromJson")
    public void testCreateVegFromJson(String rawVeg, Vegetable vegetable) {
        Vegetable actual = vegetableFactory.createVegFromJson(rawVeg);
        assertEquals(actual, vegetable);
    }

    @Test(description = "Negative scenario of the creating vegetable", dataProvider = "negativeDataForCreateFromJson")
    public void negativeDataTestCreateVegFromJson(String rawVeg) {
        assertThrows(() -> vegetableFactory.createVegFromJson(rawVeg));
    }
}