package by.training.multithreading.service.parser;

import by.training.multithreading.bean.exception.MatrixException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MatrixParserTest {
    MatrixParser parser;
    @BeforeMethod
    public void setUp() {
        parser = new MatrixParser();
    }

    @AfterMethod
    public void tearDown() {
        parser = null;
    }

    @DataProvider(name = "negativeDataForParseMatrixSize")
    public Object[] createNegativeDataFor_ParseMatrixSize() {
        String l1 = "{\"Matrix\"  \"10x10\"}";
        String l2 = "{\"Matrix\" : \"10xnumber\"}";
        String l3 = "{\"Matrix\" : \"10 10\"}";

        return new Object[]{l1, l2, l3};
    }

    @DataProvider(name = "negativeDataForParseMatrixValues")
    public Object[][] createNegativeDataFor_ParseMatrixValues() {
        return new Object[][]{
                {"1 2 3 4 5 6 7 8 9", 10},
                {"1 2 3 four 5 6 7 8 9 10", 10}
        };
    }

    @Test(description = "Positive scenario of parse matrix size from string")
    public void testParseMatrixSize() throws MatrixException {
        String header = "{\"Matrix\" : \"10x10\"}";
        int[] expected = new int[]{10, 10};
        int[] actual = parser.parseMatrixSize(header);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario of parse matrix values from string")
    public void testParseMatrixValues() throws MatrixException {
        String content = "1 2 3 4 5 6 7 8 9 10";
        int[] expected = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] actual = parser.parseMatrixValues(content, 10);
        assertEquals(actual, expected);
    }

    @Test(description = "Confirm exceptions thrown when parse matrix size",
            dataProvider = "negativeDataForParseMatrixSize",
            expectedExceptions = MatrixException.class)
    public void testParseMatrixSize_CheckThrownException(
            String header) throws MatrixException {
        parser.parseMatrixSize(header);
    }

    @Test(description = "Confirm exceptions thrown when parse matrix values",
            dataProvider = "negativeDataForParseMatrixValues",
            expectedExceptions = MatrixException.class)
    public void testParseMatrixValues_CheckThrownException(
            String content, Integer size) throws MatrixException {
        parser.parseMatrixValues(content, size);
    }
}