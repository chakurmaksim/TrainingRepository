package by.training.task1.bean.exception;

/**
 * Exception class when a string cannot be converted to a Recipe object.
 */
public class RecipeSyntaxException extends Exception {
    /**
     * Variable with content error message.
     */
    private static final String CONTENT_ERROR;
    /**
     * Variable with name error message.
     */
    private static final String NAME_ERROR;
    /**
     * Variable with amount error message.
     */
    private static final String AMOUNT_ERROR;
    static {
        CONTENT_ERROR = "File content error. "
                + "The line cannot be converted to a recipe.";
        NAME_ERROR = "Names cannot be an empty value.";
        AMOUNT_ERROR = "The amount of ingredients cannot be negative value.";
    }

    /**
     * Exception.
     * @param message error message
     */
    public RecipeSyntaxException(final String message) {
        super(message);
    }

    /**
     * Exception.
     * @param message error message
     * @param cause throwable instance
     */
    public RecipeSyntaxException(final String message, final Throwable cause) {
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
     * @return string with amount error message
     */
    public static String getAmountError() {
        return AMOUNT_ERROR;
    }
}
