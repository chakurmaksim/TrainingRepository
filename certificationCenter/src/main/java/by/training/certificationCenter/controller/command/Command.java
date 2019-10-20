package by.training.certificationCenter.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    /**
     * Events logger, in that case to register possible exceptions when
     * Service or Controller exceptions occur.
     */
    private static Logger logger = LogManager.getLogger(Command.class);
    /**
     * Relative page path.
     */
    private String pathName;
    /**
     * Variable shows whether to redirect the request or not.
     */
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
