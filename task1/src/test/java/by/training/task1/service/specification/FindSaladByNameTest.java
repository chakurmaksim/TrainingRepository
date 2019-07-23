package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FindSaladByNameTest {
    String name;
    Salad salad;

    @BeforeMethod
    public void setUp() {
        name = "salad";
        salad = new Salad(name);
    }

    @Test(description = "Confirm that comparison occurs by name")
    public void testFindSpecified() {
        Specification specification = new FindSaladByName(name);
        boolean actual = ((FindSaladByName) specification).findSpecified(salad);
        boolean expected = true;
        assertEquals(actual, expected);
    }
}