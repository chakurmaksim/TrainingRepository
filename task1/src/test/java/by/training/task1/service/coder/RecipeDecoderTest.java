package by.training.task1.service.coder;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.exception.RecipeSyntaxException;
import by.training.task1.service.factory.CoderFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class RecipeDecoderTest {
    CoderFactory coderFactory;
    RecipeDecoder recipeDecoder;
    Optional<Recipe> expected;
    Map<String, Integer> ingrMap;
    String rawRecipe;

    @BeforeTest
    public void setUp() throws Exception {
        coderFactory = CoderFactory.getSingleInstance();
        recipeDecoder = coderFactory.getRecipeDecoder();
        String[][] ingrArr = new String[][]{
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
        expected = Optional.of(new Recipe("Salad with arugula", ingrMap));
        rawRecipe = "{\"dishName\":\"Salad with arugula\"," +
                "\"composition\":{\"arugula\":120,\"dill\":30,\"parsley\":30," +
                "\"tomato\":190,\"lettuce leaves\":85}}";
    }


    @DataProvider(name = "negativeDataForCreateFromJson")
    public Object[] createNegativeDataFor_DecodeRecipe() {
        String r1 = "{\"dishName\":\"salad\",\"composition\":{\"onion\":}}";
        String r2 = "{:\"salad\",\"composition\":{\"cabbage\":400}}";
        String r3 = "{\"dishName\":,\"composition\":{\"cabbage\":400}}";
        String r4 = "{\"dishName\":\"salad\",:{\"cabbage\":400}}";
        String r5 = "{\"dishName\":\"salad\",\"composition\":{:400}}";
        return new Object[]{r1, r2, r3, r4, r5};
    }

    @Test(description = "Positive scenario for validate decode recipe")
    public void testDecodeRecipe() throws Exception {
        Optional<Recipe> actual = recipeDecoder.decodeRecipe(rawRecipe);
        assertEquals(actual, expected);
    }

    @Test(description = "Confirm exceptions thrown when creating an recipe from Json",
            dataProvider = "negativeDataForCreateFromJson",
            expectedExceptions = RecipeSyntaxException.class)
    public void testCreateRecipeFromJson_CheckThrownException(String rawRecipe)
            throws RecipeSyntaxException {
        Optional<Recipe> actual = recipeDecoder.decodeRecipe(rawRecipe);
        assertEquals(actual, expected);
    }

    @AfterTest
    public void clear() {
        coderFactory = null;
        recipeDecoder = null;
        expected = null;
        rawRecipe = null;
    }
}