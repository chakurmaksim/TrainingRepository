package by.training.multithreading.dao.filereader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public final class FileWriteReader {
    /**
     * Single instance of the FileWriteReader object.
     */
    private static FileWriteReader singleInstance;
    /**
     * single instance of the ReentrantLock object.
     */
    private static final ReentrantLock REENTRANT_LOCK;

    static {
        REENTRANT_LOCK = new ReentrantLock();
    }

    private FileWriteReader() {
    }

    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(FileWriteReader.class);

    /**
     * Method for reading all lines from the file.
     *
     * @param fileName file name
     * @return list of strings
     */
    public List<String> readAllLines(final String fileName) {
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            list = stream.filter(line -> !line.trim().equals("")).
                    collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return list;
    }

    /**
     * Method for writing list of strings to the file.
     * @param fileName file name
     * @param content list of strings
     */
    public void writeAllLines(final String fileName,
                              final List<String> content) {
        try {
            Files.write(Paths.get(fileName), content, CREATE, WRITE);
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Get method.
     *
     * @return single instance of the FileWriteReader object.
     */
    public static FileWriteReader getSingleInstance() {
        if (singleInstance == null) {
            try {
                REENTRANT_LOCK.lock();
                if (singleInstance == null) {
                    singleInstance = new FileWriteReader();
                }
            } finally {
                REENTRANT_LOCK.unlock();
            }
        }
        return singleInstance;
    }
}
