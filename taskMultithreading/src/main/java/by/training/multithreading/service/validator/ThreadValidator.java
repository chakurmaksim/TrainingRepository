package by.training.multithreading.service.validator;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

public final class ThreadValidator implements Cloneable, Serializable {
    /**
     * Variable is used during serialization.
     */
    private static final long SERIAL_VERSION_UID;
    /**
     * Maximum thread priority number corresponds to the java documentation.
     */
    private static final int MAX_PRIORITY_NUMBER = 10;
    /**
     * Minimum number of threads corresponds to the task.
     */
    private static final int MIN_THREAD_NUMBER = 4;
    /**
     * Maximum number of threads corresponds to the task.
     */
    private static final int MAX_THREAD_NUMBER = 6;
    /**
     * Single instance of the ThreadValidator object.
     */
    private static ThreadValidator singleInstance;
    /**
     * single instance of the ReentrantLock object.
     */
    private static final ReentrantLock REENTRANT_LOCK;

    static {
        SERIAL_VERSION_UID = UUID.randomUUID().getMostSignificantBits();
        REENTRANT_LOCK = new ReentrantLock();
    }

    private ThreadValidator() {
    }

    /**
     * @return single instance of the ThreadValidator object.
     */
    public static ThreadValidator getThreadValidator() {
        if (singleInstance == null) {
            REENTRANT_LOCK.lock();
            if (singleInstance == null) {
                singleInstance = new ThreadValidator();
            }
            REENTRANT_LOCK.unlock();
        }
        return singleInstance;
    }

    /**
     * Overridden clone method.
     *
     * @return single instance of the MatrixValidator object.
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return singleInstance;
    }

    protected Object readResolve() {
        return singleInstance;
    }

    /**
     * Check the limit number of threads with the requirements of the task.
     *
     * @param threadNum actual number of threads
     * @return true if actual number corresponds to the task
     */
    public boolean checkRangeThreads(final int threadNum) {
        if (threadNum >= MIN_THREAD_NUMBER && threadNum <= MAX_THREAD_NUMBER) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if threads have duplicate names.
     *
     * @param names list of thread names
     * @return true if there is no the same names
     */
    public boolean checkSameNames(final List<String> names) {
        Set<String> setNames = new HashSet<>(names);
        if (names.size() == setNames.size()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if threads have duplicate unique numbers.
     *
     * @param uniqueNums list of thread numbers
     * @return true if there is no the same numbers
     */
    public boolean checkSameNumbers(final List<Integer> uniqueNums) {
        Set<Integer> setNumbers = new HashSet<>(uniqueNums);
        if (uniqueNums.size() == setNumbers.size() && !uniqueNums.contains(0)) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if threads have correct priority numbers.
     *
     * @param priorityNums list of priority numbers
     * @return true if thread priority numbers consistent java documentation
     */
    public boolean checkRangePriorityNums(final List<Integer> priorityNums) {
        for (int number : priorityNums) {
            if (number < 1 || number > MAX_PRIORITY_NUMBER) {
                return false;
            }
        }
        return true;
    }
}
