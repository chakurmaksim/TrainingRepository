package by.training.task1.main;

import by.training.task1.hello.HelloWorld;

/**
 * The Main class for starting programme.
 */
final class Main {
    /**
     * An object of this class is not created.
     */
    private Main() {

    }

    /**
     * The whole programme starts with the main method.
     * @param args input parameters
     */
    public static void main(final String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.printGreeting("World");
    }
}
