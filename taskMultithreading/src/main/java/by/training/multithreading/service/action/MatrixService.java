package by.training.multithreading.service.action;

import by.training.multithreading.bean.entity.Matrix;
import by.training.multithreading.bean.exception.MatrixException;
import by.training.multithreading.dao.filereader.FileWriteReader;
import by.training.multithreading.dao.repository.MatrixHolder;
import by.training.multithreading.service.executor.MatrixFillUsedAtomicInt;
import by.training.multithreading.service.executor.MatrixFilling;
import by.training.multithreading.service.executor.MatrixMultiplier;
import by.training.multithreading.service.factory.MatrixCreator;
import by.training.multithreading.service.validator.MatrixValidator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixService {
    private static final int MAIN_DIAGONAL_VALUE = 0;
    private MatrixCreator creator;
    private MatrixValidator validator;
    private MatrixHolder holder;
    /**
     * Files reader repository.
     */
    private FileWriteReader fileReader;

    public MatrixService() {
        creator = MatrixCreator.getMatrixCreator();
        validator = MatrixValidator.getMatrixValidator();
        holder = MatrixHolder.SINGLE_INSTANCE;
        fileReader = FileWriteReader.getSingleInstance();
    }

    public Matrix multiplyTwoMatrixConsistently(
            final Matrix first,
            final Matrix second)
            throws MatrixException {
        Matrix result = creator.createResultMatrix(first.getVerticalSize(),
                second.getHorizontalSize());
        if (validator.couldMultiply(first, second)) {
            MatrixMultiplier.multiplySingleTh(first, second, result);
        } else {
            throw new MatrixException(MatrixException.getIncompatibleError());
        }
        return result;
    }

    public Matrix multiplyTwoMatrixConcurrently(
            final Matrix first,
            final Matrix second,
            final int numThreads)
            throws MatrixException {
        Matrix result = creator.createResultMatrix(first.getVerticalSize(),
                second.getHorizontalSize());
        if (validator.couldMultiply(first, second)) {
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
                    executor.awaitTermination(5, TimeUnit.MILLISECONDS);
                } catch (InterruptedException newE) {
                    newE.printStackTrace();
                }
            }
        } else {
            throw new MatrixException(MatrixException.getIncompatibleError());
        }
        return result;
    }

    public Matrix[] createTwoRandomMatrix(
            final int row,
            final int column,
            final int start,
            final int end) throws MatrixException {
        Matrix[] matrices = new Matrix[2];
        matrices[0] = creator.createRandomMatrix(row, column, start, end);
        matrices[1] = creator.createRandomMatrix(row, column, start, end);
        return matrices;
    }

    public Matrix fillMatrixDiagonalConcurrently(String matrixFileName)
            throws MatrixException, CloneNotSupportedException {
        putMatrixToMatrixHolder(deliverMatrixFromFile(matrixFileName));
        ReentrantLock locker = new ReentrantLock(true);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 1; i <= 4; i++) {
            executor.execute(new MatrixFilling(i, holder, locker));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                executor.awaitTermination(20, TimeUnit.MILLISECONDS);
            } catch (InterruptedException newE) {
                newE.printStackTrace();
            }
        }
        return holder.read();
    }

    public void fillMatrixDiagonalUsedAtomicInt() {
        try {
            Matrix matrix = creator.createResultMatrix(12, 12);
            holder.add(matrix);
            ReentrantLock locker = new ReentrantLock(true);
            ExecutorService executor = Executors.newFixedThreadPool(4);
            for (int i = 1; i <= 4; i++) {
                executor.execute(new MatrixFillUsedAtomicInt(i, holder, locker));
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                try {
                    executor.awaitTermination(20, TimeUnit.MILLISECONDS);
                } catch (InterruptedException newE) {
                    newE.printStackTrace();
                }
            }
            if (validator.validateMatrixDiagonal(matrix)) {
                matrix.completeState();
                System.out.println("Матрица заполнена");
            }
        } catch (MatrixException newE) {
            newE.printStackTrace();
        }
    }

    private Matrix deliverMatrixFromFile(final String fileName)
            throws MatrixException {
        List<String> list = fileReader.readAllLines(fileName);
        // list.forEach(System.out::println);
        return creator.createMatrixFromString(list);
    }

    private void putMatrixToMatrixHolder(final Matrix matrix) {
        for (int i = 0; i < matrix.getVerticalSize(); i++) {
            matrix.setElement(i, i, MAIN_DIAGONAL_VALUE);
        }
        holder.add(matrix);
    }
}
