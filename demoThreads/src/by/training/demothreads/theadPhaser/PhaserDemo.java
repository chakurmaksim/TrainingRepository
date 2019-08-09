package by.training.demothreads.theadPhaser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Phaser;

public final class PhaserDemo {
    private PhaserDemo() {
    }
    /**
     * main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Item[] goods = new Item[20];
        for (int i = 0; i < goods.length; i++) {
            goods[i] = new Item(i + 1);
        }
        List<Item> listGood = Arrays.asList(goods);
        Storage storageA = Storage.createStorage(listGood.size(), listGood);
        Storage storageB = Storage.createStorage(listGood.size());
        Phaser phaser = new Phaser();
        phaser.register();
        int currentPhase;
        Thread tr1 = new Thread(new Truck(phaser, "tr1", 5,
                storageA, storageB));
        Thread tr2 = new Thread(new Truck(phaser, "tr2", 6,
                storageA, storageB));
        Thread tr3 = new Thread(new Truck(phaser, "tr3", 7,
                storageA, storageB));
        printGoodsToConsole("Goods at the storage A", storageA);
        printGoodsToConsole("Goods at the storage B", storageB);
        tr1.start();
        tr2.start();
        tr3.start();
        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Column loading completed. Phase " + currentPhase
                + " completed.");
        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Column ride completed. Phase " + currentPhase
                + " completed.");
        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Column unloading completed. Phase " + currentPhase
                + " completed.");
        phaser.arriveAndDeregister();
        if (phaser.isTerminated()) {
            System.out.println("Phases are synchronized and completed.");
        }
        printGoodsToConsole("Goods at the storage A", storageA);
        printGoodsToConsole("Goods at the storage B", storageB);
    }

    private static void printGoodsToConsole(final String title,
                                            final Storage storage) {
        System.out.println(title);
        Iterator<Item> goodIterator = storage.iterator();
        while (goodIterator.hasNext()) {
            System.out.print(goodIterator.next().getRegistrationNumber() + " ");
        }
        System.out.println();
    }
}
