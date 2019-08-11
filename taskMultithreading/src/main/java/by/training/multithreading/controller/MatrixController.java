package by.training.multithreading.controller;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.CustomThreadException;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.bean.state.CompletedState;
import by.training.multithreading.service.action.MatrixService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatrixController {
    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(MatrixController.class);
    /**
     * Path to the file with new Matrix.
     */
    private String newMatrixFileName;
    /**
     * Path to the file with new Threads.
     */
    private String threadsFileName;
    /**
     * Path to the file with modified Matrix.
     */
    private String resultMatrixFileName;
    /**
     * Variable for keeping MatrixService instance.
     */
    private MatrixService service;

    /**
     * Constructor, where the service and file name variables are initialized.
     */
    public MatrixController() {
        service = new MatrixService();
        newMatrixFileName = "data/newMatrix.txt";
        resultMatrixFileName = "data/resultMatrix.txt";
        threadsFileName = "data/threads.txt";
    }

    /**
     * Constructor, where the service and file name variables are initialized.
     *
     * @param newNewMatrixFileName    raw matrix file name
     * @param newThreadsFileName      threads file name
     * @param newResultMatrixFileName result matrix file name
     */
    public MatrixController(
            final String newNewMatrixFileName,
            final String newThreadsFileName,
            final String newResultMatrixFileName) {
        service = new MatrixService();
        this.newMatrixFileName = newNewMatrixFileName;
        this.threadsFileName = newThreadsFileName;
        this.resultMatrixFileName = newResultMatrixFileName;
    }

    /**
     * Fill matrix main diagonal used multi-threads.
     *
     * @return string representation of the matrix
     */
    public String fillMainDiagonalConcurrently() {
        String matrixStr = "";
        try {
            Matrix matrix = service.fillMatrixDiagonalConcurrently(
                    newMatrixFileName, threadsFileName, resultMatrixFileName);
            matrix.printStatus();
            if (matrix.getState() instanceof CompletedState) {
                matrixStr = matrix.toString();
            }
        } catch (MatrixException | CustomThreadException e) {
            logger.error(e.getMessage());
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
        }
        return matrixStr;
    }

    /**
     * Fill matrix main diagonal used multi-threads and atomic integer variable.
     *
     * @return string representation of the matrix
     */
    public String fillMainDiagonalUsedAtomic() {
        String matrixStr = "";
        try {
            Matrix matrix = service.fillMatrixDiagonalUsedAtomicInt(
                    newMatrixFileName, threadsFileName, resultMatrixFileName);
            matrix.printStatus();
            if (matrix.getState() instanceof CompletedState) {
                matrixStr = matrix.toString();
            }
        } catch (MatrixException | CustomThreadException e) {
            logger.error(e.getMessage());
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
        }
        return matrixStr;
    }

    /**
     * Multiplying two matrices in one thread.
     *
     * @param row    number of matrix rows
     * @param column number of matrix columns
     * @param start  minimum number for creating matrix values
     * @param end    maximum number for creating matrix values
     * @return string representation of the matrix
     */
    public String multiplyMatricesSingleThreaded(
            final int row, final int column, final int start, final int end) {
        String marixStr = "";
        try {
            Matrix[] matrices = service.createTwoRandomMatrix(
                    row, column, start, end);
            Matrix result = service.multiplyTwoMatrixConsistently(
                    matrices[0], matrices[1]);
            marixStr = result.toString();
        } catch (MatrixException e) {
            logger.error(e.getMessage());
        }
        return marixStr;
    }

    /**
     * Multiplying two matrices in multithreading.
     *
     * @param row        number of matrix rows
     * @param column     number of matrix columns
     * @param start      minimum number for creating matrix values
     * @param end        maximum number for creating matrix values
     * @param numThreads number of threads
     * @return string representation of the matrix
     */
    public String multiplyMatricesMultiThreaded(
            final int row, final int column, final int start, final int end,
            final int numThreads) {
        String marixStr = "";
        try {
            Matrix[] matrices = service.createTwoRandomMatrix(
                    row, column, start, end);
            Matrix result = service.multiplyTwoMatrixConcurrently(
                    matrices[0], matrices[1], numThreads);
            marixStr = result.toString();
        } catch (MatrixException e) {
            logger.error(e.getMessage());
        }
        return marixStr;
    }
}
