package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.Library;

public class ParseTextCommand implements Command {
    /**
     * NavigationBar variable.
     */
    private NavigationBar bar;
    /**
     * Library variable.
     */
    private Library library;
    /**
     * ConsolePrinter variable.
     */
    private ConsolePrinter printer;

    /**
     * Initializing variables.
     *
     * @param newBar NavigationBar object
     * @param newLibrary Library object
     * @param newPrinter ConsolePrinter object
     */
    public ParseTextCommand(
            final NavigationBar newBar, final Library newLibrary,
            final ConsolePrinter newPrinter) {
        this.bar = newBar;
        this.library = newLibrary;
        this.printer = newPrinter;
    }

    /**
     * Method invokes to Library object method to parse and restore text from
     * the file and then calls next method of NavigationBar object.
     */
    @Override
    public void execute() {
        printer.printToconsole(library.parseAndRestoreTextFromFile());
        bar.continuePage();
    }
}
