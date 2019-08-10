package by.training.multithreading.service.parser;

import by.training.multithreading.bean.exception.CustomThreadException;

import static by.training.multithreading.bean.exception.CustomThreadException.getFileThreadContentError;

/**
 * The class to parse the thread from the passed string.
 */
public class ThreadParser {
    /**
     * The method parses the thread name from the passed string.
     *
     * @param rawThread passed string
     * @return thread name
     * @throws CustomThreadException when can not be parsed
     */
    public String parseThreadName(final String rawThread)
            throws CustomThreadException {
        String[] splittedRaw = splitRawThread(rawThread);
        String threadName = parseName(splittedRaw[0].trim());
        return threadName;
    }

    /**
     * The method parses the thread unique number from the passed string.
     *
     * @param rawThread passed string
     * @return unique number
     * @throws CustomThreadException when can not be parsed
     */
    public int parseThreadUniqueNumber(final String rawThread)
            throws CustomThreadException {
        String[] splittedRaw = splitRawThread(rawThread);
        String rawNumber = splittedRaw[1].trim();
        return parseThreadNumber(rawNumber.replace("}", "").trim());
    }

    /**
     * The method parses the thread priority number from the passed string.
     *
     * @param rawThread passed string
     * @return the thread priority number
     * @throws CustomThreadException when can not be parsed
     */
    public int parseThreadPriorytyNumber(final String rawThread)
            throws CustomThreadException {
        String[] splittedRaw = splitRawThread(rawThread);
        String rawNumber = splittedRaw[0].trim();
        return parseThreadNumber(rawNumber.split(",")[1].trim());
    }

    private String[] splitRawThread(final String rawThread)
            throws CustomThreadException {
        String[] splittedRaw = rawThread.split(":");
        if (splittedRaw.length != 2) {
            String message = String.format("%s: %s",
                    getFileThreadContentError(), rawThread);
            throw new CustomThreadException(message);
        }
        return splittedRaw;
    }

    private String parseName(final String rawString)
            throws CustomThreadException {
        String[] splittedRaw = rawString.split(",");
        if (splittedRaw.length != 3 || !splittedRaw[0].contains("[")) {
            String message = String.format("%s: %s",
                    getFileThreadContentError(), rawString);
            throw new CustomThreadException(message);
        }
        int squareBracketInd = splittedRaw[0].indexOf("[");
        return splittedRaw[0].substring(squareBracketInd + 1).trim();
    }

    private int parseThreadNumber(final String rawNumber)
            throws CustomThreadException {
        int number = 0;
        try {
            number = Integer.valueOf(rawNumber);
        } catch (NumberFormatException e) {
            String message = String.format("%s: matrix row contains "
                            + " not a number: %s",
                    getFileThreadContentError(), rawNumber);
            throw new CustomThreadException(message);
        }
        return number;
    }
}
