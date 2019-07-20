package by.training.task1.service.validator;

import by.training.task1.service.factory.ValidatorFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class RecipeValidatorTest {
    ValidatorFactory validatorFactory;
    RecipeValidator validator;
    String[][] ingrArr;
    Map<String, Integer>[] mapArr;

    @BeforeTest
    public void setUp() throws Exception {
        validatorFactory = ValidatorFactory.getSingleInstance();
        validator = validatorFactory.getRecipeValid();
        ingrArr = new String[][]{
                {"", "null"},
                {" ", "-30"},
                {"\r\n", "30"},
                {null, "190"},
                {"lettuce leaves", "85"}
        };
        mapArr = new Map[ingrArr.length];
        for (int i = 0; i < mapArr.length; i++) {
            mapArr[i] = new HashMap<>();
            if (ingrArr[i][1].equals("null")) {
                mapArr[i].put(ingrArr[i][0], null);
            } else {
                mapArr[i].put(ingrArr[i][0], Integer.valueOf(ingrArr[i][1]));
            }
        }
    }

    @DataProvider(name = "dataForTestIngrNameEmpty")
    public Object[][] createPositiveDataFor_ValidateIsIngrNameEmpty() {
        List<Map<String, Integer>> ingrList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ingrList.add(new HashMap<>());
        }
        return new Object[][] {
                {true, new HashMap<>()},
                {true, mapArr[0]},
                {true, mapArr[1]},
                {true, mapArr[2]},
                {true, mapArr[3]},
                {false, mapArr[4]}
        };
    }

    @DataProvider(name = "dataForTestNegativeIngrNum")
    public Object[][] createPositiveDataFor_ValidateNegativeIngrNum() {
        List<Map<String, Integer>> ingrList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ingrList.add(new HashMap<>());
        }
        return new Object[][] {
                {true, mapArr[0]},
                {true, mapArr[1]},
                {false, mapArr[2]},
        };
    }

    @Test(description = "Positive scenario for validate is recipe name empty")
    public void testValidateIsRecipeNameEmpty() {
        String[] namesArr = {"", " ", "\r\n", "name"};
        boolean[] expected = {true, true, true, false};
        boolean[] actual = new boolean[4];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = validator.validateIsRecipeNameEmpty(namesArr[i]);
        }
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario for validate is ingredient name empty",
            dataProvider = "dataForTestIngrNameEmpty")
    public void testValidateIsIngrNameEmpty(
            Boolean expected, Map<String, Integer> ingr) {
        Boolean actual = validator.validateIsIngrNameEmpty(ingr);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario for validate ingredient value is negative",
            dataProvider = "dataForTestNegativeIngrNum")
    public void testValidateNegativeIngrNum(
            Boolean expected, Map<String, Integer> ingr) {
        Boolean actual = validator.validateNegativeIngrNum(ingr);
        assertEquals(actual, expected);
    }
}