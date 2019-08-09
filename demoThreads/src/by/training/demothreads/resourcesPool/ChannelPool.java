package by.training.demothreads.resourcesPool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ChannelPool<T> {
    /**
     * pool size.
     */
    private final static int POOL_SIZE = 5;
    /**
     * Initialization and instantiating Semaphore instance.
     */
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    /**
     * Initialization and instantiating queue.
     */
    private final Queue<T> resources = new LinkedList<T>();

    /**
     * Constructor.
     *
     * @param source items
     */
    public ChannelPool(final Queue<T> source) {
        resources.addAll(source);
    }

    /**
     *
     * @param maxWaitMillis
     * @return T instance
     * @throws ResourсeException if time out or interrupted exception
     */
    public T getResource(final long maxWaitMillis) throws ResourсeException {
        try {
            if (semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
                T res = resources.poll();
                return res;
            }
        } catch (InterruptedException e) {
            throw new ResourсeException(e);
        }
        throw new ResourсeException(":timed out");
    }

    /**
     * returning an instance to the semaphore.release () pool.
     * @param res item
     */
    public void returnResource(final T res) {
        resources.add(res);
        semaphore.release();
    }
}
