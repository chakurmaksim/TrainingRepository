package by.training.multithreading.service.parser;

import by.training.multithreading.bean.exception.CustomThreadException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ThreadParserTest {
    ThreadParser parser;
    String initial;

    @BeforeMethod
    public void setUp() {
        parser = new ThreadParser();
        initial = "{\"Thread[Thread-1,1,main]\":11}";
    }

    @AfterMethod
    public void tearDown() {
        parser = null;
        initial = null;
    }

    @DataProvider(name = "negativeDataForParseThreadName")
    public Object[] createNegativeDataFor_ParseThreadName() {
        String l1 = "{\"Thread[Thread-1 1,main]\":11}";
        String l2 = "{\"Thread Thread-1,1,main]\":11}";
        String l3 = "{\"Thread[Thread-1,1,main]\" 11}";
        return new Object[]{l1, l2, l3};
    }

    @DataProvider(name = "negativeDataForParseThreadUniqueNumber")
    public Object[] createNegativeDataFor_ParseThreadUniqueNumber() {
        String l1 = "{\"Thread[Thread-1,1,main]\" 11}";
        String l2 = "{\"Thread Thread-1,1,main]\":number}";
        return new Object[]{l1, l2};
    }

    @DataProvider(name = "negativeDataForParseThreadPriorytyNumber")
    public Object[] createNegativeDataFor_ParseThreadPriorytyNumber() {
        String l1 = "{\"Thread[Thread-1 1,main]\":11}";
        String l2 = "{\"Thread[Thread-1,one,main]\":11}";
        String l3 = "{\"Thread[Thread-1,1,main]\" 11}";
        return new Object[]{l1, l2, l3};
    }

    @Test(description = "Positive scenario of parse thread name from string")
    public void testParseThreadName() throws CustomThreadException {
        String actual = parser.parseThreadName(initial);
        String expected = "Thread-1";
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario of parse thread unique number from string")
    public void testParseThreadUniqueNumber() throws CustomThreadException {
        int actual = parser.parseThreadUniqueNumber(initial);
        int expected = 11;
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario of parse thread priority number from string")
    public void testParseThreadPriorytyNumber() throws CustomThreadException {
        int actual = parser.parseThreadPriorytyNumber(initial);
        int expected = 1;
        assertEquals(actual, expected);
    }

    @Test(description = "Confirm exceptions thrown when parse thread name",
            dataProvider = "negativeDataForParseThreadName",
            expectedExceptions = CustomThreadException.class)
    public void testParseThreadName_CheckThrownException(
            String raw) throws CustomThreadException {
        parser.parseThreadName(raw);
    }

    @Test(description = "Confirm exceptions thrown when parse thread unique number",
            dataProvider = "negativeDataForParseThreadUniqueNumber",
            expectedExceptions = CustomThreadException.class)
    public void testParseThreadUniqueNumber_CheckThrownException(
            String raw) throws CustomThreadException {
        parser.parseThreadUniqueNumber(raw);
    }

    @Test(description = "Confirm exceptions thrown when parse thread priority number",
            dataProvider = "negativeDataForParseThreadPriorytyNumber",
            expectedExceptions = CustomThreadException.class)
    public void testParseThreadPriorytyNumber_CheckThrownException(
            String raw) throws CustomThreadException {
        parser.parseThreadPriorytyNumber(raw);
    }
}