package by.training.multithreading.controller;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.service.action.MatrixService;

public class MatrixController {
    /**
     * Path to the file with new Matrix.
     */
    private static String newMatrixFileName;
    /**
     * Path to the file with modified Matrix.
     */
    private static String resultMatrixFileName;
    /**
     * Variable for keeping MatrixService instance.
     */
    private MatrixService service;

    static {
        newMatrixFileName = "data/newMatrix.txt";
        resultMatrixFileName = "data/resultMatrix.txt";
    }

    /**
     * Constructor, where the service variable is initialized.
     */
    public MatrixController() {
        service = new MatrixService();
    }

    public String fillMainDiagonalConcurrently() {
        String matrixStr = "";
        try {
            Matrix matrix = service.fillMatrixDiagonalConcurrently(
                    newMatrixFileName);
            matrixStr = matrix.toString();
        } catch (MatrixException newE) {
            newE.printStackTrace();
        } catch (CloneNotSupportedException newE) {
            newE.printStackTrace();
        }
        return matrixStr;
    }
}
