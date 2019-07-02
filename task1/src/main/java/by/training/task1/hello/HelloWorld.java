package by.training.task1.hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Greeting class.
 */
public class HelloWorld {
    /**
     * The variable keeps the reference to the instance of thee Logger class.
     */
    private static final Logger LOGGER;
    /**
     * The final variable.
     */
    private static final String HELLO;
    static {
        LOGGER = LogManager.getFormatterLogger(HelloWorld.class);
        HELLO = "Hello";
    }

    /**
     * The Console output method.
     *
     * @param name of the greeting object
     */
    public void printGreeting(final String name) {
        LOGGER.info("%s, %s!", HELLO, name);
    }
}
