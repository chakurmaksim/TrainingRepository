package by.training.demothreads.threadAuction;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Bid extends Thread {
    /**
     * client bid ID.
     */
    private Integer bidId;
    /**
     * start rate.
     */
    private int price;
    /**
     * CyclicBarrier instance.
     */
    private CyclicBarrier barrier;

    /**
     * Constructor.
     *
     * @param id       client bid ID
     * @param newPrice start rate
     * @param bar      CyclicBarrier instance
     */
    public Bid(final int id, final int newPrice, final CyclicBarrier bar) {
        this.bidId = id;
        this.price = newPrice;
        this.barrier = bar;
    }

    /**
     * Get method.
     *
     * @return client bid ID
     */
    public Integer getBidId() {
        return bidId;
    }

    /**
     * Get method.
     *
     * @return client rate
     */
    public int getPrice() {
        return price;
    }

    /**
     * Overridden run method, where price are increasing.
     */
    @Override
    public void run() {
        try {
            System.out.println("Client " + this.bidId + " specifies a price.");
            Thread.sleep(new Random().nextInt(3000));
            int delta = new Random().nextInt(50);
            price += delta;
            System.out.println("Bid " + this.bidId + " : " + price);
            this.barrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
