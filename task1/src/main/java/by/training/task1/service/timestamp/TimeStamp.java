package by.training.task1.service.timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class that contains time stamp method.
 */
public final class TimeStamp {
    private TimeStamp() {
    }
    /**
     * time stamp method.
     * @return date with string format
     */
    public static String getTimeStamp() {
        return String.format("[%s]",
                LocalDateTime.now().format(DateTimeFormatter.
                        ofPattern("dd/MM/yyyy 'at' HH:mm:ss")));
    }
}
