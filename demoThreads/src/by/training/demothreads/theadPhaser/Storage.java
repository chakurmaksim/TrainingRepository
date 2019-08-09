package by.training.demothreads.theadPhaser;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class Storage implements Iterable<Item> {
    /**
     * default capacity of the storage.
     */
    public static final int DEFAULT_STORAGE_CAPACITY = 20;
    /**
     * goods queue.
     */
    private Queue<Item> goods = null;
    private Storage() {
        goods = new LinkedBlockingQueue<>(DEFAULT_STORAGE_CAPACITY);
    }
    private Storage(final int capacity) {
        goods = new LinkedBlockingQueue<Item>(capacity);
    }

    /**
     * create Storage method.
     * @param capacity of the storage
     * @return new Storage instance
     */
    public static Storage createStorage(final int capacity) {
        Storage storage = new Storage(capacity);
        return storage;
    }

    /**
     * create Storage method.
     * @param capacity of the storage
     * @param goods list of goods
     * @return Storage instance
     */
    public static Storage createStorage(final int capacity,
                                        final List<Item> goods) {
        Storage storage = new Storage(capacity);
        storage.goods.addAll(goods);
        return storage;
    }

    /**
     * get method.
     * @return one item of goods
     */
    public Item getGood() {
        return goods.poll();
    }

    /**
     * set method.
     * @param good one item of goods
     * @return true o false
     */
    public boolean setGood(final Item good) {
        return goods.add(good);
    }

    @Override
    public Iterator<Item> iterator() {
        return goods.iterator();
    }
}
