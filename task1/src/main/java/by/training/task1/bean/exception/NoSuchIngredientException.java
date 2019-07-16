package by.training.task1.bean.exception;

/**
 * Exception class when a string cannot be converted to a Vegetable object.
 */
public class NoSuchIngredientException extends IllegalArgumentException {
    /**
     * Variable with content error message.
     */
    private static final String CONTENT_ERROR;
    /**
     * Variable with name error message.
     */
    private static final String NAME_ERROR;
    /**
     * Variable with group name error message.
     */
    private static final String GROUP_ERROR;
    /**
     * Variable with amount error message.
     */
    private static final String AMOUNT_ERROR;
    static {
        CONTENT_ERROR = "File content error. "
                + "The line cannot be converted to a vegetable";
        NAME_ERROR = "Vegetable name cannot be an empty value";
        GROUP_ERROR = "Such group of vegetables does not exist";
        AMOUNT_ERROR = "The amount of nutrients cannot be less than zero";
    }

    /**
     * Exception.
     * @param message error message
     */
    public NoSuchIngredientException(final String message) {
        super(message);
    }

    /**
     * Exception.
     * @param message error message
     * @param cause throwable instance
     */
    public NoSuchIngredientException(final String message,
                                     final Throwable cause) {
        super(message, cause);
    }

    /**
     * Get method.
     * @return string with content error message
     */
    public static String getContentError() {
        return CONTENT_ERROR;
    }

    /**
     * Get method.
     * @return string with name error message
     */
    public static String getNameError() {
        return NAME_ERROR;
    }

    /**
     * Get method.
     * @return string with group name error message
     */
    public static String getGroupError() {
        return GROUP_ERROR;
    }

    /**
     * Get method.
     * @return string with amount error message
     */
    public static String getAmountError() {
        return AMOUNT_ERROR;
    }
}
