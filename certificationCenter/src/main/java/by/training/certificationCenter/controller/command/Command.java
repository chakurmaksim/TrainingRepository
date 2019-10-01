package by.training.certificationCenter.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    private static Logger logger = LogManager.getLogger(Command.class);
    private String pathName;
    private boolean isRedirect;
    /**
     * Method calls to a certain operation of business logic.
     */
    public abstract void execute(HttpServletRequest request, HttpServletResponse response);

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public static Logger getLogger() {
        return logger;
    }
}
