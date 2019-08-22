package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.ActionChooser;
import by.training.taskComposite.service.action.Library;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class NavigationBar {
    /**
     * Given symbol for sorting lexemes in a text.
     */
    private static final char GIVEN_SYMBOL = 'a';
    /**
     * Logger to output data into the log file.
     */
    private Logger errorLogger = LogManager.getLogger();
    /**
     * ActionChooser variable.
     */
    private ActionChooser chooser;
    /**
     * Library variable.
     */
    private Library library;
    /**
     * Declaration of the console reader variable.
     */
    private BufferedReader consoleReader;
    /**
     * Declaration of the console printer variable.
     */
    private ConsolePrinter consolePrinter;

    /**
     * Instantiating locale, bundle, library and console reader variables.
     */
    public NavigationBar() {
        chooser = new ActionChooser();
        library = new Library();
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        consolePrinter = new ConsolePrinter();
    }

    /**
     * Program navigation.
     */
    public void startPage() {
        HashMap<Integer, Command> commandMap = new HashMap<>();
        int commandCount = 1;
        commandMap.put(commandCount, new OfferLanguageCommand(
                this, chooser, consolePrinter));
        commandMap.put(++commandCount, new ParseTextCommand(this,
                library, consolePrinter));
        commandMap.put(++commandCount, new CommandExit(
                consoleReader, errorLogger));
        consolePrinter.printToconsole(chooser.offerStartAction());
        int userInput = 0;
        try {
            userInput = Integer.parseInt(consoleReader.readLine());
            Command command = commandMap.get(userInput);
            if (command != null) {
                command.execute();
            } else {
                startPage();
            }
        } catch (NumberFormatException | IOException e) {
            errorLogger.error(e.toString());
        }
    }

    /**
     * Method invokes sorting actions and is called after start page.
     */
    void continuePage() {
        HashMap<Integer, Command> commandMap = new HashMap<>();
        int commandCount = 1;
        commandMap.put(commandCount, new SortParagraphsCommand(
                this, library, consolePrinter));
        commandMap.put(++commandCount, new SortWordsCommand(
                this, library, consolePrinter));
        commandMap.put(++commandCount, new SortSentencesCommand(
                this, library, consolePrinter));
        commandMap.put(++commandCount, new SortLexemesCommand(
                this, library, consolePrinter, GIVEN_SYMBOL));
        commandMap.put(++commandCount, new CommandExit(
                consoleReader, errorLogger));
        consolePrinter.printToconsole(chooser.offerSortAction());
        int userInput = 0;
        try {
            userInput = Integer.parseInt(consoleReader.readLine());
            Command command = commandMap.get(userInput);
            if (command != null) {
                command.execute();
            } else {
                continuePage();
            }
        } catch (NumberFormatException | IOException e) {
            errorLogger.error(e.toString());
        }
    }

    /**
     * Method invokes choosing language of the navigation menu.
     */
    void chooseLanguage() {
        int userInput = 0;
        try {
            userInput = Integer.parseInt(consoleReader.readLine());
            ChooseLanguageCommand command = new ChooseLanguageCommand(
                    this, chooser, userInput);
            command.execute();
        } catch (NumberFormatException | IOException e) {
            errorLogger.error(e.toString());
        }
    }
}
