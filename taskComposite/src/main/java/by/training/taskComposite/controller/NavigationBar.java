package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.Library;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class NavigationBar {
    /**
     * Final variable with the path to the properties file.
     */
    private static final String RESOURCE_FILE_NAME;
    /**
     * Logger to output data into the console.
     */
    private Logger rootLogger = LogManager.getRootLogger();
    /**
     * Logger to output data into the log file.
     */
    private Logger errorLogger = LogManager.getLogger();
    /**
     * Default language is English.
     */
    private String language = "en";
    /**
     * Default country.
     */
    private String country = "EN";
    /**
     * Declaration of the Locale variable.
     */
    private Locale locale;
    /**
     * Declaration of the ResourceBundle variable.
     */
    private ResourceBundle bundle;
    /**
     * Library variable.
     */
    private Library library;
    /**
     * Declaration of the console reader variable.
     */
    private BufferedReader consoleReader;

    static {
        RESOURCE_FILE_NAME = "text";
    }

    /**
     * Instantiating locale, bundle, library and console reader variables.
     */
    public NavigationBar() {
        locale = new Locale(language, country);
        bundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale);
        library = new Library();
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Program navigation.
     */
    public void startPage() {
        String page = String.format("1 - %s; \n2 - %s; \n3 - %s.",
                bundle.getString("language.choose"),
                bundle.getString("action.textFromFile"),
                bundle.getString("exit"));
        rootLogger.info(page);
        int i = 0;
        try {
            i = Integer.parseInt(consoleReader.readLine());
            switch (i) {
                case 1:
                    chooseLanguage();
                    break;
                case 2:
                    readTextFromFile();
                    break;
                case 3:
                    consoleReader.close();
                    return;
            }
        } catch (NumberFormatException | IOException e) {
            errorLogger.error(e.toString());
        }
    }

    private void continuePage() {
        String page = String.format("1 - %s; \n2 - %s; \n3 - %s.",
                bundle.getString("action.sortBySentencesAmount"),
                bundle.getString("action.sortByWordsAmount"),
                bundle.getString("exit"));
        rootLogger.info(page);
        int i = 0;
        try {
            i = Integer.parseInt(consoleReader.readLine());
            switch (i) {
                case 1:
                    sortParagraphs();
                    break;
                case 2:
                    sortSentences();
                    break;
                case 3:
                    consoleReader.close();
                    return;
            }
        } catch (NumberFormatException | IOException e) {
            errorLogger.error(e.toString());
        }
    }

    private void chooseLanguage() {
        String choosing = String.format("1 - %s; 2 - %s; 3 - %s: ",
                bundle.getString("language.english"),
                bundle.getString("language.deutsch"),
                bundle.getString("language.russian"));
        rootLogger.info(choosing);
        int i = 0;
        try {
            i = Integer.parseInt(consoleReader.readLine());
            switch (i) {
                case 1:
                    country = "EN";
                    language = "en";
                    break;
                case 2:
                    country = "DE";
                    language = "de";
                    break;
                case 3:
                    country = "RU";
                    language = "ru";
                    break;
            }
            locale = new Locale(language, country);
            bundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale);
            startPage();
        } catch (NumberFormatException | IOException e) {
            errorLogger.error(e.toString());
        }
    }

    private void readTextFromFile() {
        rootLogger.info(library.parseAndRestoreTextFromFile());
        continuePage();
    }

    private void sortParagraphs() {
        rootLogger.info(library.sortParagraphsBySentencesAmount());
    }

    private void sortSentences() {
        rootLogger.info(library.sortSentencesByWordsAmount());
    }
}
