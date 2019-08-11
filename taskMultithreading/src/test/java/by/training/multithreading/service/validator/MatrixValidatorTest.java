package by.training.multithreading.service.validator;

import by.training.multithreading.bean.entity.Matrix;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MatrixValidatorTest {
    MatrixValidator validator;
    @BeforeMethod
    public void setUp() {
        validator = MatrixValidator.getMatrixValidator();
    }

    @AfterMethod
    public void tearDown() {
        validator = null;
    }

    @DataProvider(name = "dataForTestCheckRange")
    public Object[][] createPositiveDataFor_CheckRange() {
        return new Object[][] {
                {8, true},
                {12, true},
                {7, false},
                {13, false}
        };
    }

    @DataProvider(name = "dataForTestCouldMultiply")
    public Object[][] createPositiveDataFor_CouldMultiply() {
        return new Object[][] {
                {new Matrix[]{
                        new Matrix(8, 8), new Matrix(8, 8)
                }, true},
                {new Matrix[]{
                        new Matrix(12, 12), new Matrix(8, 8)
                }, false}
        };
    }

    @DataProvider(name = "dataForTestValidateMatrixDiagonal")
    public Object[][] createPositiveDataFor_ValidateMatrixDiagonal() {
        int[][] initialArray = new int[][]{
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8}
        };
        Matrix initialMatrix = new Matrix(
                initialArray.length, initialArray[0].length);
        for (int i = 0; i < initialMatrix.getVerticalSize(); i++) {
            for (int j = 0; j < initialMatrix.getHorizontalSize(); j++) {
                initialMatrix.setElement(i, j, initialArray[i][j]);
            }
        }
        return new Object[][] {
                {initialMatrix, true},
                {new Matrix(8, 8), false}
        };
    }

    @Test(description = "Positive scenario for check range matrix size",
            dataProvider = "dataForTestCheckRange")
    public void testCheckRange(Integer row, Boolean expected) {
        Boolean actual = validator.checkRange(row, row);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario for check could multiply matrices",
            dataProvider = "dataForTestCouldMultiply")
    public void testCouldMultiply(Matrix[] matrices, Boolean expected) {
        Boolean actual = validator.couldMultiply(matrices[0], matrices[1]);
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario for validate existing zeros in the "
            + "main diagonal of the matrix",
            dataProvider = "dataForTestValidateMatrixDiagonal")
    public void testValidateMatrixDiagonal(Matrix matrix, Boolean expected) {
        Boolean actual = validator.validateMatrixDiagonal(matrix);
        assertEquals(actual, expected);
    }
}