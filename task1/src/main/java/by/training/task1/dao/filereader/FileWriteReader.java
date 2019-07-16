package by.training.task1.dao.filereader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Optional;

public class FileWriteReader {
    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(FileWriteReader.class);
    /**
     * file reader variable.
     */
    private FileReader fileReader;
    /**
     * buffered reader variable.
     */
    private BufferedReader bufferedReader;
    /**
     * file output stream variable.
     */
    private FileOutputStream fileOutputStream;
    /**
     * output stream writer variable.
     */
    private OutputStreamWriter outputStreamWriter;
    /**
     * buffered writer.
     */
    private BufferedWriter bufferedWriter;

    /**
     * Method to open input stream for file reading.
     *
     * @param fileName name of the file
     */
    public void openFileReader(final String fileName) {
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException();
        }
    }

    /**
     * Method to close input stream.
     */
    public void closeFileReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Method for reading next line from the file.
     *
     * @return object of class optional which contains String object
     */
    public Optional<String> readNextLine() {
        Optional<String> optionalRaw = Optional.empty();
        try {
            String rawVeg = bufferedReader.readLine();
            optionalRaw = Optional.ofNullable(rawVeg);
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return optionalRaw;
    }

    /**
     * Method to open output stream for file writing.
     *
     * @param fileName name of the file
     * @param append   false if the file needs to be overwritten
     */
    public void openFileWriter(final String fileName, final boolean append) {
        try {
            fileOutputStream = new FileOutputStream(fileName, append);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException();
        }
    }

    /**
     * Method to close output stream.
     */
    public void closeFileWriter() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Method for writing string to the file.
     * @param rawStr string
     */
    public void writeNextLine(final String rawStr) {
        try {
            bufferedWriter.write(rawStr + "\r\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }
}
