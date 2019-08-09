package by.training.demothreads.threadAuction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CyclicBarrier;

public class Auction {
    /**
     * list of bids.
     */
    private ArrayList<Bid> bids;
    /**
     * CyclicBarrier instance.
     */
    private CyclicBarrier barrier;
    /**
     * quantity of bids.
     */
    public final int BIDS_NUMBER = 5;

    /**
     * This constructor initializes CyclicBarrier instance.
     */
    public Auction() {
        this.bids = new ArrayList<Bid>();
        this.barrier = new CyclicBarrier(this.BIDS_NUMBER, new Runnable() {
            public void run() {
                Bid winner = Auction.this.defineWinner();
                System.out.println("Bid #" + winner.getBidId() + ", price:"
                        + winner.getPrice() + " win!");
            }
        });
    }

    /**
     * Get method.
     *
     * @return CyclicBarrier instance
     */
    public CyclicBarrier getBarrier() {
        return barrier;
    }

    /**
     *Add bid method.
     * @param e new bid
     * @return true if bid was added
     */
    public boolean add(final Bid e) {
        return bids.add(e);
    }

    /**
     * Overridden run method, where occurs comparison bids.
     * @return max rate
     */
    public Bid defineWinner() {
        return Collections.max(bids, new Comparator<Bid>() {
            @Override
            public int compare(final Bid ob1, final Bid ob2) {
                return ob1.getPrice() - ob2.getPrice();
            }
        });
    }
}
