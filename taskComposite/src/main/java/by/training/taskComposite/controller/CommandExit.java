package by.training.taskComposite.controller;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class CommandExit implements Command {
    /**
     * Declaration of the console reader variable.
     */
    private BufferedReader consoleReader;
    /**
     * Logger to output data into the log file.
     */
    private Logger errorLogger;

    /**
     * Initializing variables.
     *
     * @param newConsoleReader BufferedReader object
     * @param newErrorLogger Logger object
     */
    public CommandExit(final BufferedReader newConsoleReader,
                       final Logger newErrorLogger) {
        this.consoleReader = newConsoleReader;
        this.errorLogger = newErrorLogger;
    }

    /**
     * Release of resources and leave the program.
     */
    @Override
    public void execute() {
        try {
            consoleReader.close();
        } catch (IOException e) {
            errorLogger.error(e.toString());
        }
    }
}
