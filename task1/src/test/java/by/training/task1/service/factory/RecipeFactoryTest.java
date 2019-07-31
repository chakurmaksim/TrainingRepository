package by.training.task1.service.factory;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.exception.RecipeSyntaxException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class RecipeFactoryTest {
    RecipeFactory recipeFactory;
    Recipe expected;
    String[][] ingrArr;
    Map<String, Integer> ingrMap;
    String dishName;

    @BeforeTest
    public void setUp() throws Exception {
        recipeFactory = RecipeFactory.getSingleInstance();
        dishName = "Salad with arugula";
        ingrArr = new String[][]{
                {"arugula", "120"},
                {"dill", "30"},
                {"parsley", "30"},
                {"tomato", "190"},
                {"lettuce leaves", "85"}
        };
        ingrMap = new HashMap<>();
        for (int i = 0; i < ingrArr.length; i++) {
            ingrMap.put(ingrArr[i][0], Integer.valueOf(ingrArr[i][1]));
        }
        expected = new Recipe(dishName, ingrMap);
    }

    @DataProvider(name = "negativeDataForCreateFromJson")
    public Object[] createNegativeDataFor_CreateRecipeFromJson() {
        String r1 = "{\" \":\"salad\",\"composition\":{\"red pepper\":80}}";
        String r2 = "{\"dishName\":\" \",\"composition\":{\"arugula\":120}}";
        String r3 = "{\"dishName\":\"salad\",\" \":{\"spinach\":400}}";
        String r4 = "{\"dishName\":\"salad\",\"composition\":{\" \":50}}";
        String r5 = "{\"dishName\":\"salad\",\"composition\":{\"onion\":}}";
        String r6 = "{\"dishName\":\"salad\",\"composition\":{\"spinach\":-50}}";
        String r7 = "{:\"salad\",\"composition\":{\"cabbage\":400}}";
        String r8 = "{\"dishName\":,\"composition\":{\"cabbage\":400}}";
        String r9 = "{\"dishName\":\"salad\",:{\"cabbage\":400}}";
        String r10 = "{\"dishName\":\"salad\",\"composition\":{:400}}";
        return new Object[]{r1, r2, r3, r4, r5, r6, r7, r8, r9, r10};
    }

    @DataProvider(name = "negativeDataForCreateCertainRecipe")
    public Object[] createNegativeDataFor_CreateCertainRecipe() {
        return new Object[][]{
                {" ", ingrMap},
                {"name", null},
                {"name", new HashMap<String, Integer>()},
                {"name", new HashMap<String, Integer>().put(" ", 100)},
                {"name", new HashMap<String, Integer>().put("name", -100)},
                {"name", new HashMap<String, Integer>().put(null, 100)},
                {"name", new HashMap<String, Integer>().put("name", null)},
        };
    }

    @Test(description = "Positive scenario of creating recipe from Json")
    public void testCreateRecipeFromJson() throws Exception {
        String rawRecipe = "{\"dishName\":\"Salad with arugula\","
                + "\"composition\":{\"arugula\":120,\"dill\":30,\"parsley\":30,"
                + "\"tomato\":190,\"lettuce leaves\":85}}";
        Recipe actual = recipeFactory.createRecipeFromJson(rawRecipe);

        assertEquals(actual, expected);
    }

    @Test(description = "Negative scenario of creating recipe from Json",
            dataProvider = "negativeDataForCreateFromJson")
    public void negativeDataTestCreateRecipeFromJson(String rawRecipe) {
        assertThrows(() -> recipeFactory.createRecipeFromJson(rawRecipe));
    }

    @Test(description = "Confirm exceptions thrown when creating an recipe from Json",
            dataProvider = "negativeDataForCreateFromJson",
            expectedExceptions = RecipeSyntaxException.class)
    public void testCreateRecipeFromJson_CheckThrownException(String rawRecipe)
            throws RecipeSyntaxException {
        Recipe actual = recipeFactory.createRecipeFromJson(rawRecipe);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario of creating certain recipe")
    public void testCreateCertainRecipe() throws RecipeSyntaxException {
        Recipe actual = recipeFactory.createCertainRecipe(dishName, ingrMap);
        assertEquals(actual, expected);
    }

    @Test(description = "Negative scenario of creating certain recipe",
            dataProvider = "negativeDataForCreateCertainRecipe")
    public void negativeDataTestCreateCertainRecipe(
            String name, Map<String, Integer> ingr) {
        assertThrows(() -> recipeFactory.createCertainRecipe(name, ingr));
    }

    @Test(description = "Confirm exceptions thrown when creating a recipe",
            dataProvider = "negativeDataForCreateCertainRecipe",
            expectedExceptions = RecipeSyntaxException.class)
    public void testCreateCertainRecipe_CheckThrownException(
            String name, Map<String, Integer> ingr)
            throws RecipeSyntaxException {
        recipeFactory.createCertainRecipe(name, ingr);
    }

    @AfterTest
    public void clear() {
        recipeFactory = null;
        expected = null;
        ingrArr = null;
        dishName = null;
    }
}