package by.training.multithreading.dao.filereader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

public final class FileWriteReader {
    private static FileWriteReader SINGLE_INSTANCE;
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
     * Method for reading next line from the file.
     *
     * @return object of class optional which contains String object
     */
    public List<String> readAllLines(String fileName) {
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            list = stream.filter(line -> !line.trim().equals("")).
                    collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return list;
    }

    public void writeAllLines(final String fileName, List<String> content) {
        try {
            Files.write(Paths.get(fileName), content, WRITE);
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
        if (SINGLE_INSTANCE == null) {
            try {
                REENTRANT_LOCK.lock();
                if (SINGLE_INSTANCE == null) {
                    SINGLE_INSTANCE = new FileWriteReader();
                }
            } finally {
                REENTRANT_LOCK.unlock();
            }
        }
        return SINGLE_INSTANCE;
    }
}
