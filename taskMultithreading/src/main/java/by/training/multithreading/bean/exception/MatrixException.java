package by.training.multithreading.bean.exception;

/**
 * Exception class when matrix can not be created, modified or got.
 */
public class MatrixException extends Exception {
    /**
     * Variable with matrix size error message.
     */
    private static final String SIZE_ERROR;
    /**
     * Variable with matrix incompatibility message.
     */
    private static final String INCOMPATIBLE_ERROR;
    /**
     * Variable with a message about a nonexistent matrix.
     */
    private static final String NULLABLE_ERROR;
    /**
     * Variable with a message about damaged data in a matrix-file.
     */
    private static final String FILE_MATRIX_CONTENT_ERROR;
    /**
     * Variable with a message about damaged data in a thread-file.
     */
    private static final String FILE_THREAD_CONTENT_ERROR;

    static {
        SIZE_ERROR = "Size of the matrix does not match the task";
        INCOMPATIBLE_ERROR = "Length of the matrix row does not match "
                + "the length of the column of the second matrix";
        NULLABLE_ERROR = "Matrix does not exist at MatrixDao";
        FILE_MATRIX_CONTENT_ERROR = "File with the matrix contains "
                + "corrupted data";
        FILE_THREAD_CONTENT_ERROR = "File with the threads contains "
                + "corrupted data";
    }

    /**
     * Exception.
     *
     * @param message error message
     */
    public MatrixException(String message) {
        super(message);
    }

    /**
     * Exception.
     *
     * @param message error message
     * @param cause   Throwable instance
     */
    public MatrixException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Get method.
     *
     * @return string with matrix size error message.
     */
    public static String getSizeError() {
        return SIZE_ERROR;
    }

    /**
     * Get method.
     *
     * @return string with matrix incompatibility message
     */
    public static String getIncompatibleError() {
        return INCOMPATIBLE_ERROR;
    }

    /**
     * Get method.
     *
     * @return string with a message about a nonexistent matrix.
     */
    public static String getNullableError() {
        return NULLABLE_ERROR;
    }

    /**
     * Get method.
     *
     * @return string with a message about damaged data in a matrix-file.
     */
    public static String getFileMatrixContentError() {
        return FILE_MATRIX_CONTENT_ERROR;
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
