package by.training.demothreads.theadPhaser;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Truck implements Runnable {
    /**
     * Phaser instance.
     */
    private Phaser phaser;
    /**
     * truck number.
     */
    private String number;
    /**
     * truck capacity.
     */
    private int capacity;
    /**
     * link to the storage where where the download is in progress.
     */
    private Storage storafeFrom;
    /**
     * link to the storage where where the unload is in progress.
     */
    private Storage storageTo;
    /**
     * items queue.
     */
    private Queue<Item> bodyStorage;

    /**
     * Constructor to instantiate object variables.
     * @param newPhaser Phaser instance
     * @param newNumber truck number
     * @param newCapacity truck capacity
     * @param newStorafeFrom storage
     * @param newStorageTo storage
     */
    public Truck(final Phaser newPhaser, final String newNumber,
                 final int newCapacity, final Storage newStorafeFrom,
                 final Storage newStorageTo) {
        phaser = newPhaser;
        number = newNumber;
        capacity = newCapacity;
        storafeFrom = newStorafeFrom;
        storageTo = newStorageTo;
        bodyStorage = new ArrayDeque<>(capacity);
        this.phaser.register();
    }

    /**
     * Overridden run method.
     */
    @Override
    public void run() {
        loadTruck();
        phaser.arriveAndAwaitAdvance();
        goTruck();
        phaser.arriveAndAwaitAdvance();
        unloadTruck();
        phaser.arriveAndDeregister();
    }

    private void loadTruck() {
        for (int i = 0; i < capacity; i++) {
            Item g = storafeFrom.getGood();
            if (g == null) {
                return;
            }
            bodyStorage.add(g);
            System.out.println("Грузовик " + number + " загрузил товар No"
                    + g.getRegistrationNumber());
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void unloadTruck() {
        int size = bodyStorage.size();
        for (int i = 0; i < size; i++) {
            Item g = bodyStorage.poll();
            storageTo.setGood(g);
            System.out.println("Грузовик " + number + " разгрузил товар No"
                    + g.getRegistrationNumber());
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void goTruck() {
        try {
            Thread.sleep(new Random(100).nextInt(500));
            System.out.println("Грузовик " + number + " начал поездку.");
            Thread.sleep(new Random(100).nextInt(1000));
            System.out.println("Грузовик " + number + " завершил поездку.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
