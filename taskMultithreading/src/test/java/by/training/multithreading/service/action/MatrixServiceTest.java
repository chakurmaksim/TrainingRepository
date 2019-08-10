package by.training.multithreading.service.action;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MatrixServiceTest {
    int[][] initialArray;
    Matrix initialMatrix;
    int[][] expected;
    MatrixService service;
    @BeforeTest
    public void setUp() throws Exception {
        service = new MatrixService();
        initialArray = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1}
        };
        expected = new int[][]{
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8}
        };
        initialMatrix = new Matrix(initialArray.length, initialArray[0].length);
        for (int i = 0; i < initialMatrix.getVerticalSize(); i++) {
            for (int j = 0; j < initialMatrix.getHorizontalSize(); j++) {
                initialMatrix.setElement(i, j, initialArray[i][j]);
            }
        }
    }

    @Test(description = "Positive scenario of multiplying two matrices")
    public void testMultiplyTwoMatrixConsistently() throws MatrixException {
        Matrix result = service.multiplyTwoMatrixConsistently(
                initialMatrix, initialMatrix);
        int rows = result.getVerticalSize();
        int columns = result.getHorizontalSize();
        int[][] actual = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                actual[i][j] = result.getElement(i, j);
            }
            assertEquals(actual[i], expected[i]);
        }
    }

    @Test(description = "Positive scenario of multiplying two matrices")
    public void testMultiplyTwoMatrixConcurrently() throws MatrixException {
        Matrix result = service.multiplyTwoMatrixConcurrently(
                initialMatrix, initialMatrix, 3);
        int rows = result.getVerticalSize();
        int columns = result.getHorizontalSize();
        int[][] actual = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                actual[i][j] = result.getElement(i, j);
            }
            assertEquals(actual[i], expected[i]);
        }
    }

    @Test(description = "Confirm exceptions thrown when multiplying "
            + "two matrices of different sizes in one thread",
            expectedExceptions = MatrixException.class)
    public void testMultiplyTwoMatrixConsistently_CheckThrownException()
            throws MatrixException {
        service.multiplyTwoMatrixConsistently(new Matrix(12, 12),
                new Matrix(8, 8));
    }

    @Test(description = "Confirm exceptions thrown when multiplying "
            + "two matrices of different sizes in multi thread",
            expectedExceptions = MatrixException.class)
    public void testMultiplyTwoMatrixConcurrently_CheckThrownException()
            throws MatrixException {
        service.multiplyTwoMatrixConcurrently(new Matrix(12, 12),
                new Matrix(8, 8), 3);
    }

    @AfterTest
    public void clear() {
        service = null;
        expected = null;
        initialArray = null;
        initialMatrix = null;
    }
}