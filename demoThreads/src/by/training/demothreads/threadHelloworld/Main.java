package by.training.demothreads.threadHelloworld;

public final class Main {
    private Main() {
    }

    /**
     * main method to start the program.
     *
     * @param args arguments.
     */
    public static void main(final String[] args) {
        Thread thread = new ThreadHello();
        thread.start();
    }
}
