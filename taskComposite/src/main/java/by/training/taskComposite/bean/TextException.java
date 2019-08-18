package by.training.taskComposite.bean;

public class TextException extends Exception {
    /**
     * Variable with a message about a nonexistent matrix.
     */
    private static final String NULLABLE_ERROR;

    static {
        NULLABLE_ERROR = "Text does not exist at TextHandler";
    }

    /**
     * Exception.
     *
     * @param message error message
     */
    public TextException(final String message) {
        super(message);
    }

    /**
     * Get method.
     *
     * @return message with nullable error.
     */
    public static String getNullableError() {
        return NULLABLE_ERROR;
    }
}
