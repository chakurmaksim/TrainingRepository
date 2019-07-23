package by.training.task1.service.factory.certainvegetablefactory;

import by.training.task1.bean.entity.RootVegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RootVegFactoryTest {
    RootVegetable expected;
    RootVegFactory factory;

    @BeforeMethod
    public void setUp() {
        factory = RootVegFactory.getSingleInstance();
        expected = new RootVegetable("carrot");
        expected.setKcalPer100g(32);
        expected.setProteinsPer100g(1.3);
        expected.setFatsPer100g(0.1);
        expected.setCarbohydratesPer100g(6.9);

    }

    @AfterMethod
    public void tearDown() {
        expected = null;
        factory = null;
    }

    @DataProvider(name = "negativeDataForCreateCertainVeg")
    public Object[] createNegativeDataFor_CreateCertainVeg() {
        RootVegetable[] vegetables = new RootVegetable[5];
        for (int i = 0; i < vegetables.length; i++) {
            vegetables[i] = new RootVegetable("carrot");
            vegetables[i].setKcalPer100g(32);
            vegetables[i].setProteinsPer100g(1.3);
            vegetables[i].setFatsPer100g(0.1);
            vegetables[i].setCarbohydratesPer100g(6.9);
            switch (i) {
                case 0:
                    vegetables[i].setVegName(null);
                    break;
                case 1:
                    vegetables[i].setVegName("");
                    break;
                case 2:
                    vegetables[i].setCarbohydratesPer100g(-73);
                    break;
                case 3:
                    vegetables[i].setFatsPer100g(-0.2);
                    break;
                case 4:
                    vegetables[i].setProteinsPer100g(-5.0);
                    break;
            }
        }
        return new Object[]{vegetables[0], vegetables[1], vegetables[2],
                vegetables[3], vegetables[4]
        };
    }

    @Test
    public void testCreateCertainVeg() {
        RootVegetable actual = factory.createCertainVeg(
                expected.getVegName(),
                expected.getKcalPer100g(),
                expected.getProteinsPer100g(),
                expected.getFatsPer100g(),
                expected.getCarbohydratesPer100g());
        assertEquals(actual, expected);
    }

    @Test(description = "Negative scenario of creating vegetable",
            dataProvider = "negativeDataForCreateCertainVeg",
            expectedExceptions = NoSuchIngredientException.class)
    public void negativeDataTestCreateCertainVeg(RootVegetable vegetable)
            throws NoSuchIngredientException {
        RootVegetable actual = factory.createCertainVeg(
                vegetable.getVegName(),
                vegetable.getKcalPer100g(),
                vegetable.getProteinsPer100g(),
                vegetable.getFatsPer100g(),
                vegetable.getCarbohydratesPer100g());
        assertEquals(actual, expected);
    }
}