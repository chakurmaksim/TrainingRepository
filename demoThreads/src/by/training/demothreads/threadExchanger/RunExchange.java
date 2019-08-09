package by.training.demothreads.threadExchanger;

public final class RunExchange {
    private RunExchange() {
    }

    /**
     * main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        Item ss1 = new Item(34, 2200);
        Item ss2 = new Item(34, 2100);
        new Thread(new Producer("HP ", ss1)).start();
        new Thread(new Consumer("RETAIL Trade", ss2)).start();
    }
}
