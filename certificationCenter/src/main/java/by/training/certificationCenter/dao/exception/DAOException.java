package by.training.certificationCenter.dao.exception;

public class DAOException extends Exception {
    /**
     * Message for unsupported methods.
     */
    private static final String UNSUPPORTED_OPERATION;

    static {
        UNSUPPORTED_OPERATION = "Method %s at the %s is not implemented and "
                + "is not supported";
    }

    public DAOException(final String message) {
        super(message);
    }

    public DAOException(final String message, final Throwable e) {
        super(message, e);
    }

    public static String getUnsupportedOperation(
            final String methodName, final String className) {
        return String.format(UNSUPPORTED_OPERATION, methodName, className);
    }
}
