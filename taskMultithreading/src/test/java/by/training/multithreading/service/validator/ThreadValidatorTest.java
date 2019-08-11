package by.training.multithreading.service.validator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ThreadValidatorTest {
    ThreadValidator validator;

    @BeforeMethod
    public void setUp() {
        validator = ThreadValidator.getThreadValidator();
    }

    @AfterMethod
    public void tearDown() {
        validator = null;
    }

    @DataProvider(name = "dataForTestCheckRangeThreads")
    public Object[][] createPositiveDataFor_CheckRangeThreads() {
        return new Object[][] {
                {4, true},
                {6, true},
                {3, false},
                {7, false}
        };
    }

    @DataProvider(name = "dataForTestCheckSameNames")
    public Object[][] createPositiveDataFor_CheckSameNames() {
        return new Object[][] {
                {Arrays.asList(new String[]{
                        "Thread-1", "Thread-2", "Thread-3", "Thread-4"
                }), true},
                {Arrays.asList(new String[]{
                        "Thread-1", "Thread-1", "Thread-3", "Thread-4"
                }), false}
        };
    }

    @DataProvider(name = "dataForTestCheckSameNumbers")
    public Object[][] createPositiveDataFor_CheckSameNumbers() {
        return new Object[][] {
                {Arrays.asList(new Integer[]{
                        11, 22, 33, 44
                }), true},
                {Arrays.asList(new Integer[]{
                        11, 11, 33, 44
                }), false},
                {Arrays.asList(new Integer[]{
                        0, 22, 33, 44
                }), false}
        };
    }

    @DataProvider(name = "dataForTestCheckRangePriorityNums")
    public Object[][] createPositiveDataFor_CheckRangePriorityNums() {
        return new Object[][] {
                {Arrays.asList(new Integer[]{
                        1, 5, 7, 10
                }), true},
                {Arrays.asList(new Integer[]{
                        0, 5, 7, 10
                }), false},
                {Arrays.asList(new Integer[]{
                        1, 5, 7, 11
                }), false}
        };
    }

    @Test(description = "Positive scenario for check range threads",
            dataProvider = "dataForTestCheckRangeThreads")
    public void testCheckRangeThreads(Integer numThreads, Boolean expected) {
        Boolean actual = validator.checkRangeThreads(numThreads);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario for check same names",
            dataProvider = "dataForTestCheckSameNames")
    public void testCheckSameNames(List<String> names, Boolean expected) {
        Boolean actual = validator.checkSameNames(names);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario for check same numbers",
            dataProvider = "dataForTestCheckSameNumbers")
    public void testCheckSameNumbers(List<Integer> numbers, Boolean expected) {
        Boolean actual = validator.checkSameNumbers(numbers);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario for check range priority numbers",
            dataProvider = "dataForTestCheckRangePriorityNums")
    public void testCheckRangePriorityNums(
            List<Integer> priorNums, Boolean expected) {
        Boolean actual = validator.checkRangePriorityNums(priorNums);
        assertEquals(actual, expected);
    }
}