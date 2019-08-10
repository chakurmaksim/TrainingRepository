package by.training.multithreading.service.factory;

import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Class to create threads.
 */
public class CustomThreadFactory implements ThreadFactory {
    /**
     * list with the thread names.
     */
    private List<String> threadNames;
    /**
     * list with the thread priority numbers.
     */
    private List<Integer> threadPriorities;
    /**
     * Maximum number of the treads.
     */
    private int amountThreads;
    /**
     * Counter of already created threads.
     */
    private int counter;

    /**
     * Constructor to initialize list with the thread names, list with the
     * thread priority numbers, number of the treads and counter.
     *
     * @param newThreadNames      list with the thread names
     * @param newThreadPriorities list with the thread priority numbers
     */
    public CustomThreadFactory(final List<String> newThreadNames,
                               final List<Integer> newThreadPriorities) {
        threadNames = newThreadNames;
        threadPriorities = newThreadPriorities;
        amountThreads = newThreadNames.size();
        counter = 0;
    }

    /**
     * Overridden method where are created the new threads.
     *
     * @param r Runnable object
     * @return Thread object
     */
    @Override
    public Thread newThread(final Runnable r) {
        Thread thread;
        if (counter < amountThreads) {
            thread = new Thread(r, threadNames.get(counter));
            thread.setPriority(threadPriorities.get(counter));
            counter++;
        } else {
            thread = new Thread();
        }
        return thread;
    }
}
