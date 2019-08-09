package by.training.demothreads.threadAuction;

import java.util.Random;

public final class AuctionRunner {
    private AuctionRunner() {
    }
    /**
     * main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        Auction auction = new Auction();
        int startPrice = new Random().nextInt(100);
        for (int i = 0; i < auction.BIDS_NUMBER; i++) {
            Bid thread = new Bid(i, startPrice, auction.getBarrier());
            auction.add(thread);
            thread.start();
        }
    }
}
