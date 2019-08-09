package by.training.multithreading.main;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.controller.MatrixController;
import by.training.multithreading.dao.filereader.FileWriteReader;
import by.training.multithreading.dao.repository.MatrixHolder;
import by.training.multithreading.service.action.MatrixService;

import java.util.Arrays;

public final class Main {
    private Main() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        MatrixController controller = new MatrixController();
        MatrixService matrixService = new MatrixService();
        try {
            Matrix[] matrices = matrixService.createTwoRandomMatrix(
                    12, 12, 1, 10);
            long timeStart = System.currentTimeMillis();
            Matrix res = matrixService.multiplyTwoMatrixConsistently(
                    matrices[0], matrices[1]);
            long timeEnd = System.currentTimeMillis();
            System.out.println("Time was spent to multiplySingleTh two matrices: "
                    + (timeEnd - timeStart) + " millis");
            // System.out.println(matrices[0] + " * " + matrices[1] + " = " + res);

            timeStart = System.currentTimeMillis();
            res = matrixService.multiplyTwoMatrixConcurrently(
                    matrices[0], matrices[1], 4);
            timeEnd = System.currentTimeMillis();
            System.out.println("Time was spent to multiplyMultiTh two matrices: "
                    + (timeEnd - timeStart) + " millis");
            // System.out.println(" = " + res);

        } catch (MatrixException newE) {
            newE.printStackTrace();
        }

        System.out.println(controller.fillMainDiagonalConcurrently());

        matrixService.fillMatrixDiagonalUsedAtomicInt();
        Matrix matrix = null;
        try {
            matrix = MatrixHolder.SINGLE_INSTANCE.read();
        } catch (MatrixException e) {

        } catch (CloneNotSupportedException newE) {
            newE.printStackTrace();
        }
        System.out.println(matrix.toString());
    }
}
