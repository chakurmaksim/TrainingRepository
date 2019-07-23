package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FindSaladByIdTest {
    Salad salad;

    @BeforeMethod
    public void setUp() {
        salad = new Salad("salad");
        salad.setSaladID(1);
    }

    @Test(description = "Confirm that comparison occurs by id")
    public void testFindSpecified() {
        Specification specification = new FindSaladById(1);
        boolean actual = ((FindSaladById) specification).findSpecified(salad);
        boolean expected = true;
        assertEquals(actual, expected);
    }
}