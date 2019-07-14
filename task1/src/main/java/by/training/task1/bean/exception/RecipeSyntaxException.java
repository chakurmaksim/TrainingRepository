package by.training.task1.bean.exception;

public class RecipeSyntaxException extends Exception {
    private static final String CONTENT_ERROR;
    private static final String NAME_ERROR;
    private static final String AMOUNT_ERROR;
    static {
        CONTENT_ERROR = "File content error. The line cannot be converted to a recipe.";
        NAME_ERROR = "Name cannot be an empty value.";
        AMOUNT_ERROR = "The amount of ingredients cannot be less than zero.";
    }

    public RecipeSyntaxException(String message) {
        super(message);
    }

    public RecipeSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public static String getContentError() {
        return CONTENT_ERROR;
    }

    public static String getNameError() {
        return NAME_ERROR;
    }

    public static String getAmountError() {
        return AMOUNT_ERROR;
    }
}
