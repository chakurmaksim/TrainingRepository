package by.training.multithreading.service.factory;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class MatrixCreatorTest {
    MatrixCreator creator;
    List<String> initialList;
    Matrix expected;

    @BeforeMethod
    public void setUp() {
        creator = MatrixCreator.getMatrixCreator();
        expected = new Matrix(8, 8);
        initialList = new ArrayList<>();
        String header = "{\"Matrix\" : \"8x8\"}";
        String valuesStr = "1 1 1 1 1 1 1 1";
        initialList.add(header);
        for (int i = 0; i < expected.getVerticalSize(); i++) {
            initialList.add(valuesStr);
            for (int j = 0; j < expected.getHorizontalSize(); j++) {
                expected.setElement(i, j, 1);
            }
        }
    }

    @DataProvider(name = "negativeDataForCreateMatrixFromString")
    public Object[] createNegativeDataFor_CreateMatrixFromString() {
        List<String> baseList = new ArrayList<>();
        baseList.add("{\"Matrix\" : \"8x8\"}");
        for (int i = 0; i < 8; i++) {
            baseList.add("1 1 1 1 1 1 1 1");
        }
        List<String> l1 = new ArrayList<>(baseList);
        l1.set(0, "\"Matrix\" : \"8x8\"}");
        List<String> l2 = new ArrayList<>(baseList);
        l2.set(0, "{\"Matrix\"  \"8x8\"}");
        List<String> l3 = new ArrayList<>(baseList);
        l3.set(0, "{\"Matrix\" : \"12x12\"}");
        List<String> l4 = new ArrayList<>(baseList);
        l4.set(1, "1 1 1 number 1 1 1 1");
        return new Object[]{l1, l2, l3, l4};
    }

    @AfterMethod
    public void tearDown() {
        creator = null;
        initialList = null;
        expected = null;
    }

    @Test(description = "Positive scenario of creating matrix from list of strings")
    public void testCreateMatrixFromString() throws MatrixException {
        Matrix actual = creator.createMatrixFromString(initialList);
        assertEquals(actual.toString(), expected.toString());
    }

    @Test(description = "Confirm exceptions thrown when creating a matrix",
            dataProvider = "negativeDataForCreateMatrixFromString",
            expectedExceptions = MatrixException.class)
    public void testCreateMatrixFromString_CheckThrownException(
            List<String> list) throws MatrixException {
        creator.createMatrixFromString(list);
    }
}