package by.training.certificationCenter.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    private static Logger logger = LogManager.getLogger(Command.class);
    private String forwardPathName;
    /**
     * Method calls to a certain operation of business logic.
     */
    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    public String getForwardPathName() {
        return forwardPathName;
    }

    public void setForwardPathName(String forwardPathName) {
        this.forwardPathName = forwardPathName;
    }

    public static Logger getLogger() {
        return logger;
    }
}
