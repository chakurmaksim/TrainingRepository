package by.training.multithreading.service.factory;

import by.training.multithreading.bean.exception.CustomThreadException;
import by.training.multithreading.dao.repository.MatrixHolder;
import by.training.multithreading.service.executor.MatrixFillConcurrently;
import by.training.multithreading.service.executor.MatrixFillUsedAtomicInt;
import by.training.multithreading.service.parser.ThreadParser;
import by.training.multithreading.service.validator.ThreadValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static by.training.multithreading.bean.exception.CustomThreadException.getFileThreadContentError;
import static by.training.multithreading.bean.exception.CustomThreadException.getSizeError;

/**
 * Thread creator class.
 */
public final class ThreadCreator {
    /**
     * Nested class to create ThreadCreator instance.
     */
    private static class ThreadCreatorHolder {
        /**
         * Variable for keeping ThreadCreator instance.
         */
        private static final ThreadCreator SINGLE_INSTANCE;

        static {
            SINGLE_INSTANCE = new ThreadCreator();
        }
    }

    /**
     * Declaration ThreadValidator instance.
     */
    private ThreadValidator validator;
    /**
     * Declaration ThreadParser instance.
     */
    private ThreadParser parser;
    /**
     * Declaration MatrixHolder instance.
     */
    private MatrixHolder holder;

    private ThreadCreator() {
        validator = ThreadValidator.getThreadValidator();
        parser = new ThreadParser();
        holder = MatrixHolder.SINGLE_INSTANCE;
    }

    /**
     * Get method.
     *
     * @return single instance of the ThreadCreator.
     */
    public static ThreadCreator getThreadCreator() {
        return ThreadCreatorHolder.SINGLE_INSTANCE;
    }

    /**
     * Creates new threads from the list of rows that are read from the file.
     *
     * @param rawList list of rows
     * @return list of threads based on MatrixFillConcurrently objects
     * @throws CustomThreadException when can not be parsed or passed validation
     */
    public List<Thread> createThreadsForFillConcurrently(
            final List<String> rawList) throws CustomThreadException {
        validateListSize(rawList);
        List<String> threadNames = new LinkedList<>();
        List<Integer> uniqueNums = new LinkedList<>();
        List<Integer> priorityNums = new LinkedList<>();
        parseThreadsFromString(rawList, threadNames, uniqueNums, priorityNums);
        validateRawData(uniqueNums, threadNames, priorityNums);
        List<Runnable> matrixRunners = new LinkedList<>();
        ReentrantLock locker = new ReentrantLock(true);
        for (int uniqueNumber : uniqueNums) {
            matrixRunners.add(new MatrixFillConcurrently(uniqueNumber,
                    holder, locker));
        }
        return createThreads(threadNames, priorityNums, matrixRunners);
    }

    /**
     * Creates new threads from the list of rows that are read from the file.
     *
     * @param rawList list of rows
     * @return list of threads based on MatrixFillUsedAtomicInt objects
     * @throws CustomThreadException when can not be parsed or passed validation
     */
    public List<Thread> createThreadsForFillUsedAtomic(
            final List<String> rawList) throws CustomThreadException {
        validateListSize(rawList);
        List<String> threadNames = new LinkedList<>();
        List<Integer> uniqueNums = new LinkedList<>();
        List<Integer> priorityNums = new LinkedList<>();
        parseThreadsFromString(rawList, threadNames, uniqueNums, priorityNums);
        validateRawData(uniqueNums, threadNames, priorityNums);
        List<Runnable> matrixRunners = new LinkedList<>();
        for (int uniqueNumber : uniqueNums) {
            matrixRunners.add(new MatrixFillUsedAtomicInt(
                    uniqueNumber, holder));
        }
        return createThreads(threadNames, priorityNums, matrixRunners);
    }

    private boolean validateListSize(final List<String> list)
            throws CustomThreadException {
        if (list.size() == 0) {
            String message = String.format("%s: is empty",
                    getFileThreadContentError());
            throw new CustomThreadException(message);
        }
        if (!validator.checkRangeThreads(list.size())) {
            String message = String.format("%s: actual %d", getSizeError(),
                    list.size());
            throw new CustomThreadException(message);
        }
        return true;
    }

    private boolean parseThreadsFromString(
            final List<String> rawList, final List<String> threadNames,
            final List<Integer> uniqueNums, final List<Integer> priorityNums)
            throws CustomThreadException {
        for (String threadsStr : rawList) {
            if (threadsStr.trim().startsWith("{\"Thread")
                    && threadsStr.trim().endsWith("}")) {
                threadNames.add(parser.parseThreadName(threadsStr));
                priorityNums.add(parser.parseThreadPriorytyNumber(threadsStr));
                int uniqueNumber = parser.parseThreadUniqueNumber(threadsStr);
                uniqueNums.add(uniqueNumber);
            } else {
                String message = String.format("%s: %s",
                        getFileThreadContentError(), threadsStr);
                throw new CustomThreadException(message);
            }
        }
        return true;
    }

    private boolean validateRawData(
            final List<Integer> uniqueNums,
            final List<String> threadNames,
            final List<Integer> priorityNums) throws CustomThreadException {
        if (!validator.checkSameNumbers(uniqueNums)) {
            String message = String.format("%s: threads have duplicate "
                            + "numbers: %s", getFileThreadContentError(),
                    uniqueNums.toString());
            throw new CustomThreadException(message);
        }
        if (!validator.checkSameNames(threadNames)) {
            String message = String.format("%s: threads have duplicate "
                            + "names: %s", getFileThreadContentError(),
                    threadNames.toString());
            throw new CustomThreadException(message);
        }
        if (!validator.checkRangePriorityNums(priorityNums)) {
            String message = String.format("%s: threads have incorrect "
                            + "priority numbers: %s",
                    getFileThreadContentError(), priorityNums.toString());
            throw new CustomThreadException(message);
        }
        return true;
    }

    private List<Thread> createThreads(
            final List<String> threadNames,
            final List<Integer> priorityNums,
            final List<Runnable> matrixRunners) {
        CustomThreadFactory threadFactory = new CustomThreadFactory(
                threadNames, priorityNums);
        List<Thread> threads = new LinkedList<>();
        for (Runnable runner : matrixRunners) {
            threads.add(threadFactory.newThread(runner));
        }
        return threads;
    }
}
