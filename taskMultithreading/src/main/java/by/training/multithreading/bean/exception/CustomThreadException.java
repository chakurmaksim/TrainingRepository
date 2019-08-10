package by.training.multithreading.bean.exception;

public class CustomThreadException extends Exception {
    /**
     * Variable with matrix size error message.
     */
    private static final String SIZE_ERROR;
    /**
     * Variable with a message about damaged data in a thread-file.
     */
    private static final String FILE_THREAD_CONTENT_ERROR;

    static {
        SIZE_ERROR = "Amount of the threads does not match the task";
        FILE_THREAD_CONTENT_ERROR = "File with the threads contains "
                + "corrupted data";
    }

    /**
     * Exception.
     *
     * @param message error message
     */
    public CustomThreadException(final String message) {
        super(message);
    }

    /**
     * Get method.
     *
     * @return string with threads amount error message.
     */
    public static String getSizeError() {
        return SIZE_ERROR;
    }

    /**
     * Get method.
     *
     * @return string with a message about damaged data in a thread-file.
     */
    public static String getFileThreadContentError() {
        return FILE_THREAD_CONTENT_ERROR;
    }
}
