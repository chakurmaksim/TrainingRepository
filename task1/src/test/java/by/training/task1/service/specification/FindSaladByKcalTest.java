package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FindSaladByKcalTest {
    Salad salad;

    @BeforeMethod
    public void setUp() {
        salad = new Salad("salad");
        salad.setKcal(30);
    }

    @Test(description = "Test searching salad by kcal")
    public void testFindSpecified() {
        Specification specification = new FindSaladByKcal(20, 40);
        boolean actual1 = ((FindSaladByKcal) specification).findSpecified(salad);
        boolean expected1 = true;
        specification = new FindSaladByKcal(40, 60);
        boolean actual2 = ((FindSaladByKcal) specification).findSpecified(salad);
        boolean expected2 = false;
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
    }
}