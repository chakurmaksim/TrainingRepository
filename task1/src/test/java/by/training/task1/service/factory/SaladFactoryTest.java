package by.training.task1.service.factory;

import by.training.task1.bean.entity.Salad;
import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.entity.Vegetable;
import by.training.task1.bean.entity.FruitVegetable;
import by.training.task1.bean.entity.BulbousVegetable;
import by.training.task1.bean.exception.RecipeSyntaxException;
import by.training.task1.service.timestamp.TimeStamp;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class SaladFactoryTest {
    private SaladFactory saladFactory;
    private String saladName;
    private Recipe recipe;
    private String[][] ingrArr;
    private Map<String, Integer> ingrMap;
    private Vegetable[] vegArr;
    private Map<Vegetable, Integer> vegMap;
    private Salad expected;

    @BeforeTest
    public void setUp() throws Exception {
        saladFactory = SaladFactory.getSingleInstance();
        saladName = "Salad";
        ingrArr = new String[][]{
                {"cucumber", "100"},
                {"tomato", "100"},
                {"onion", "30"},
        };
        ingrMap = new HashMap<>();
        for (int i = 0; i < ingrArr.length; i++) {
            ingrMap.put(ingrArr[i][0], Integer.valueOf(ingrArr[i][1]));
        }
        recipe = RecipeFactory.getSingleInstance().createCertainRecipe(
                saladName, ingrMap);
        vegArr = new Vegetable[]{
                new FruitVegetable(ingrArr[0][0]),
                new FruitVegetable(ingrArr[1][0]),
                new BulbousVegetable(ingrArr[2][0])
        };
        for (int i = 0; i < vegArr.length; i++) {
            vegArr[i].setKcalPer100g(20);
            vegArr[i].setProteinsPer100g(1.1);
            vegArr[i].setFatsPer100g(0.2);
            vegArr[i].setCarbohydratesPer100g(3.7);
        }
        expected = new Salad(saladName);
        vegMap = new HashMap<>();
        for (int i = 0; i < vegArr.length; i++) {
            vegMap.put(vegArr[i], Integer.valueOf(ingrArr[i][1]));
        }
        expected.setIngredients(vegMap);
        expected.setDate(TimeStamp.getTimeStamp());
        int weight = 0;
        double kcal = 0;
        for (Map.Entry<Vegetable, Integer> entry
                : expected.getIngredients().entrySet()) {
            weight += entry.getValue();
            kcal += entry.getKey().getKcalPer100g() * entry.getValue() / 100;
        }
        expected.setWeight(weight);
        expected.setKcal(kcal);
        expected.setKcalPer100g(kcal * 100 / weight);
    }

    @DataProvider(name = "negativeDataForCreateSalad")
    public Object[] createNegativeDataFor_CreateCertainRecipe() {
        List<Vegetable> vegetableList = new ArrayList<>(vegMap.keySet());
        vegetableList.remove(0);
        return new Object[][]{
                {recipe, new ArrayList<Vegetable>()},
                {recipe, vegetableList}
        };
    }

    @Test(description = "Positive scenario of creating salad")
    public void testMakeSalad() throws Exception {
        List<Vegetable> vegetableList = new ArrayList<>(vegMap.keySet());
        Salad actual = saladFactory.makeSalad(
                recipe, vegetableList);
        assertEquals(actual, expected);
    }

    @Test(description = "Negative scenario of creating salad",
            dataProvider = "negativeDataForCreateSalad",
            expectedExceptions = RecipeSyntaxException.class)
    public void negativeDataMakeSalad(Recipe rec, List<Vegetable> list)
            throws RecipeSyntaxException {
        saladFactory.makeSalad(rec, list);
    }

    @AfterTest
    public void clear() {
        saladFactory = null;
        saladName = null;
        expected = null;
        ingrArr = null;
        ingrMap = null;
        vegArr = null;
        vegMap = null;
    }
}