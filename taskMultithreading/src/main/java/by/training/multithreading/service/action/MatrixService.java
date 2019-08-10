package by.training.multithreading.service.action;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.CustomThreadException;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.dao.filereader.FileWriteReader;
import by.training.multithreading.dao.repository.MatrixHolder;
import by.training.multithreading.service.executor.MatrixMultiplier;
import by.training.multithreading.service.factory.MatrixCreator;
import by.training.multithreading.service.factory.ThreadCreator;
import by.training.multithreading.service.validator.MatrixValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixService {
    /**
     * The initial values ​​to fill the  main diagonal.
     */
    private static final int MAIN_DIAGONAL_VALUE = 0;
    /**
     * Time to pause thread execution.
     */
    private static final int TIME_AWAIT = 5;
    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(MatrixService.class);
    /**
     * Matrix creator.
     */
    private MatrixCreator matrixCreator;
    /**
     * Thread creator.
     */
    private ThreadCreator threadCreator;
    /**
     * Matrix validator.
     */
    private MatrixValidator matrixValidator;
    /**
     * Matrix storage.
     */
    private MatrixHolder matrixHolder;
    /**
     * Files reader repository.
     */
    private FileWriteReader fileReader;

    /**
     * Matrix creator, thread creator, matrix holder and validator, file reader
     * variables are initialized in this constructor.
     */
    public MatrixService() {
        matrixCreator = MatrixCreator.getMatrixCreator();
        threadCreator = ThreadCreator.getThreadCreator();
        matrixValidator = MatrixValidator.getMatrixValidator();
        matrixHolder = MatrixHolder.SINGLE_INSTANCE;
        fileReader = FileWriteReader.getSingleInstance();
    }

    /**
     * Multiplying two matrices in one thread.
     *
     * @param first  matrix object
     * @param second matrix object
     * @return result matrix object
     * @throws MatrixException if matrices can not be multiplied
     */
    public Matrix multiplyTwoMatrixConsistently(
            final Matrix first,
            final Matrix second)
            throws MatrixException {
        Matrix res = matrixCreator.createResultMatrix(first.getVerticalSize(),
                second.getHorizontalSize());
        if (!matrixValidator.couldMultiply(first, second)) {
            throw new MatrixException(MatrixException.getIncompatibleError());
        }
        MatrixMultiplier.multiplySingleThreaded(first, second, res);
        return res;
    }

    /**
     * Multiplying two matrices in multithreading.
     *
     * @param first      matrix object
     * @param second     matrix object
     * @param numThreads thread number
     * @return result matrix object
     * @throws MatrixException if matrices can not be multiplied
     */
    public Matrix multiplyTwoMatrixConcurrently(
            final Matrix first,
            final Matrix second,
            final int numThreads)
            throws MatrixException {
        Matrix result = matrixCreator.createResultMatrix(
                first.getVerticalSize(), second.getHorizontalSize());
        if (matrixValidator.couldMultiply(first, second)) {
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            int mid = Math.round((float) first.getVerticalSize() / numThreads);
            int startInd = 0;
            int endInd = mid;
            for (int i = 0; i < numThreads; i++) {
                executor.execute(new MatrixMultiplier(first, second,
                        result, startInd, endInd));
                startInd += mid;
                if (startInd >= first.getVerticalSize()) {
                    break;
                }
                endInd += mid;
                if (endInd > first.getVerticalSize()) {
                    endInd = first.getVerticalSize();
                }
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                try {
                    executor.awaitTermination(TIME_AWAIT,
                            TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    String message = String.format("%s %s", e.toString(),
                            Thread.currentThread().toString());
                    logger.error(message);
                }
            }
        } else {
            throw new MatrixException(MatrixException.getIncompatibleError());
        }
        return result;
    }

    /**
     * Create two random matrices.
     *
     * @param row    number of matrix rows
     * @param column number of matrix columns
     * @param start  minimum number for creating matrix values
     * @param end    maximum number for creating matrix values
     * @return array that contains two matrices
     * @throws MatrixException if matrices can not be created
     */
    public Matrix[] createTwoRandomMatrix(
            final int row,
            final int column,
            final int start,
            final int end) throws MatrixException {
        Matrix[] matrices = new Matrix[2];
        matrices[0] = matrixCreator.createRandomMatrix(row, column, start, end);
        matrices[1] = matrixCreator.createRandomMatrix(row, column, start, end);
        return matrices;
    }

    /**
     * Fill matrix main diagonal used multi-threads.
     *
     * @param rawMatrixFileName    raw matrix file name
     * @param threadsFileName      threads file name
     * @param resultMatrixFileName result matrix file name
     * @return Matrix object
     * @throws MatrixException            when matrix can not be created
     * @throws CustomThreadException      when threads can not be created
     * @throws CloneNotSupportedException when matrix can not be cloned
     */
    public Matrix fillMatrixDiagonalConcurrently(
            final String rawMatrixFileName, final String threadsFileName,
            final String resultMatrixFileName) throws MatrixException,
            CustomThreadException, CloneNotSupportedException {
        putRawMatrixToMatrixHolder(deliverMatrixFromFile(rawMatrixFileName));
        List<Thread> threads = deliverThreadsForFillConcurrently(
                threadsFileName);
        startThreads(threads);
        Matrix completedMatrix = matrixHolder.read();
        if (matrixValidator.validateMatrixDiagonal(completedMatrix)) {
            completedMatrix.nextState();
            fileReader.writeAllLines(resultMatrixFileName,
                    new ArrayList<>(Arrays.asList(completedMatrix.toString())));
        }
        return completedMatrix;
    }

    /**
     * Fill matrix main diagonal used multi-threads and atomic integer variable.
     *
     * @param rawMatrixFileName    raw matrix file name
     * @param threadsFileName      threads file name
     * @param resultMatrixFileName result matrix file name
     * @return Matrix object
     * @throws MatrixException            when matrix can not be created
     * @throws CustomThreadException      when threads can not be created
     * @throws CloneNotSupportedException when matrix can not be cloned
     */
    public Matrix fillMatrixDiagonalUsedAtomicInt(
            final String rawMatrixFileName, final String threadsFileName,
            final String resultMatrixFileName) throws MatrixException,
            CustomThreadException, CloneNotSupportedException {
        putRawMatrixToMatrixHolder(deliverMatrixFromFile(rawMatrixFileName));
        List<Thread> threads = deliverThreadsForUsedAtomic(
                threadsFileName);
        startThreads(threads);
        Matrix completedMatrix = matrixHolder.read();
        if (matrixValidator.validateMatrixDiagonal(completedMatrix)) {
            completedMatrix.nextState();
            fileReader.writeAllLines(resultMatrixFileName,
                    new ArrayList<>(Arrays.asList(completedMatrix.toString())));
        }
        return completedMatrix;
    }

    private Matrix deliverMatrixFromFile(final String fileName)
            throws MatrixException {
        List<String> rawList = fileReader.readAllLines(fileName);
        return matrixCreator.createMatrixFromString(rawList);
    }

    private void putRawMatrixToMatrixHolder(final Matrix matrix) {
        for (int i = 0; i < matrix.getVerticalSize(); i++) {
            matrix.setElement(i, i, MAIN_DIAGONAL_VALUE);
        }
        matrix.nextState();
        matrixHolder.add(matrix);
    }

    private List<Thread> deliverThreadsForFillConcurrently(
            final String fileName) throws CustomThreadException {
        List<String> rawList = fileReader.readAllLines(fileName);
        return threadCreator.createThreadsForFillConcurrently(rawList);
    }

    private List<Thread> deliverThreadsForUsedAtomic(final String fileName)
            throws CustomThreadException {
        List<String> rawList = fileReader.readAllLines(fileName);
        return threadCreator.createThreadsForFillUsedAtomic(rawList);
    }

    private void startThreads(final List<Thread> threads) {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
            if (i == threads.size() - 1) {
                try {
                    threads.get(i).join();
                } catch (InterruptedException e) {
                    logger.error(e.toString());
                }
            }
        }
    }
}
