package by.training.task1.service.factory.certainvegetablefactory;

import by.training.task1.bean.entity.LeafyVegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LeafyVegFactoryTest {
    LeafyVegetable expected;
    LeafyVegFactory factory;

    @BeforeMethod
    public void setUp() {
        factory = LeafyVegFactory.getSingleInstance();
        expected = new LeafyVegetable("dill");
        expected.setKcalPer100g(38);
        expected.setProteinsPer100g(2.5);
        expected.setFatsPer100g(0.5);
        expected.setCarbohydratesPer100g(6.3);

    }

    @AfterMethod
    public void tearDown() {
        expected = null;
        factory = null;
    }

    @DataProvider(name = "negativeDataForCreateCertainVeg")
    public Object[] createNegativeDataFor_CreateCertainVeg() {
        LeafyVegetable[] vegetables = new LeafyVegetable[5];
        for (int i = 0; i < vegetables.length; i++) {
            vegetables[i] = new LeafyVegetable("dill");
            vegetables[i].setKcalPer100g(38);
            vegetables[i].setProteinsPer100g(2.5);
            vegetables[i].setFatsPer100g(0.5);
            vegetables[i].setCarbohydratesPer100g(6.3);
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
        LeafyVegetable actual = factory.createCertainVeg(
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
    public void negativeDataTestCreateCertainVeg(LeafyVegetable vegetable)
            throws NoSuchIngredientException {
        LeafyVegetable actual = factory.createCertainVeg(
                vegetable.getVegName(),
                vegetable.getKcalPer100g(),
                vegetable.getProteinsPer100g(),
                vegetable.getFatsPer100g(),
                vegetable.getCarbohydratesPer100g());
        assertEquals(actual, expected);
    }
}