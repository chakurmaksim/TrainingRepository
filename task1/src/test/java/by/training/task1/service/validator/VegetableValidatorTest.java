package by.training.task1.service.validator;

import by.training.task1.bean.enumerations.VegetableGroup;
import by.training.task1.service.factory.ValidatorFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class VegetableValidatorTest {
    ValidatorFactory validatorFactory;
    VegetableValidator vegValidator;

    @BeforeTest
    public void setUp() throws Exception {
        validatorFactory = ValidatorFactory.getSingleInstance();
        vegValidator = validatorFactory.getVegetableValid();
    }

    @Test
    public void testValidateGroupName() {
        VegetableGroup[] groupArr = VegetableGroup.values();
        boolean[] actual = new boolean[groupArr.length + 1];
        for (int i = 0; i < groupArr.length; i++) {
            actual[i] = vegValidator.validateGroupName(groupArr[i].getGroupName());
        }
        actual[actual.length - 1] = vegValidator.validateGroupName("vegetable");
        boolean[] expected = new boolean[actual.length];
        for (int i = 0; i < expected.length - 1; i++) {
            expected[i] = true;
        }
        expected[expected.length - 1] = false;
        assertEquals(actual, expected);
    }

    @Test
    public void testValidateIsNameEmpty() {
        String[] namesArr = {"", " ", "\r\n", "name"};
        boolean[] expected = {true, true, true, false};
        boolean[] actual = new boolean[4];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = vegValidator.validateIsNameEmpty(namesArr[i]);
        }
        assertEquals(actual, expected);
    }

    @Test
    public void testValidateNegCaloriesNum() {
        int kcal = -1;
        boolean actual = vegValidator.validateNegCaloriesNum(kcal);
        boolean expected = true;
        assertEquals(actual, expected);
    }

    @Test
    public void testValidateNegNutrientNum() {
        double nutrient = -1.0;
        boolean actual = vegValidator.validateNegNutrientNum(nutrient);
        boolean expected = true;
        assertEquals(actual, expected);
    }
}