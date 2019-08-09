package by.training.demothreads.resourcesPool;

public class ResourсeException extends Exception {
    /**
     * Constructor without arguments.
     */
    public ResourсeException() {
        super();
    }

    /**
     * Constructor with two arguments.
     *
     * @param message error message
     * @param cause   which are caused exception
     */
    public ResourсeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with one argument.
     *
     * @param message error message
     */
    public ResourсeException(final String message) {
        super(message);
    }

    /**
     * Constructor with one argument.
     *
     * @param cause which are caused exception
     */
    public ResourсeException(final Throwable cause) {
        super(cause);
    }
}
