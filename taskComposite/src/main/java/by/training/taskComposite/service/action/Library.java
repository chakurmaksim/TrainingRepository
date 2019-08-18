package by.training.taskComposite.service.action;

import by.training.task1.dao.filereader.FileWriteReader;
import by.training.task1.service.configuration.ApplicationProperties;
import by.training.taskComposite.bean.Text;
import by.training.taskComposite.dao.TextHandler;
import by.training.taskComposite.service.parsers.TextParser;
import by.training.taskComposite.service.specification.SortParagraphs;
import by.training.taskComposite.service.specification.SortSentencesByWordsAmount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class Library {
    /**
     * Path to the file with the text.
     */
    private static String inputFileName;
    /**
     * Path to the file with the result.
     */
    private static String outputFileName;
    /**
     * File reader.
     */
    private FileWriteReader fileReader;
    /**
     * Storage with the text object.
     */
    private TextHandler textHandler;

    static {
        Properties properties = ApplicationProperties.getProperties();
        inputFileName = properties.getProperty("inputFileName");
        outputFileName = properties.getProperty("outputFileName");
    }

    /**
     * Constructor to initialize file reader and text handler variables.
     */
    public Library() {
        fileReader = new FileWriteReader();
        textHandler = TextHandler.SINGLE_INSTANCE;
    }

    /**
     * Method to read data from the file, parse raw strings and restore.
     *
     * @return strings of the text
     */
    public String parseAndRestoreTextFromFile() {
        deliverTextFromFile();
        return textHandler.read();
    }

    /**
     * Method to sort paragraphs by sentences length.
     *
     * @return sorted strings of the text
     */
    public String sortParagraphsBySentencesAmount() {
        return textHandler.query(new SortParagraphs());
    }

    /**
     * Method to sort sentences by words amount.
     *
     * @return sorted strings of the text
     */
    public String sortSentencesByWordsAmount() {
        return textHandler.query(new SortSentencesByWordsAmount());
    }

    private void deliverTextFromFile() {
        fileReader.openFileReader(inputFileName);
        Optional<String> optionalLine;
        List<String> textList = new ArrayList<>();
        do {
            optionalLine = fileReader.readNextLine();
            if (optionalLine.isPresent()) {
                String line = optionalLine.get();
                textList.add(line);
            }
        } while (optionalLine.isPresent());
        fileReader.closeFileReader();
        createText(textList);
    }

    private void createText(List<String> textList) {
        String initialText = textList.stream().collect(
                Collectors.joining("\n"));
        Text text = new Text();
        new TextParser().parse(text, initialText);
        textHandler.add(text);
    }
}
