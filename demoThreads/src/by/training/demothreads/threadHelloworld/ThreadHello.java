package by.training.demothreads.threadHelloworld;

public class ThreadHello extends Thread {

    /**
     * Overridden run method.
     */
    @Override
    public void run() {
        System.out.println(currentThread() + ": Hello, World!");
    }
}
