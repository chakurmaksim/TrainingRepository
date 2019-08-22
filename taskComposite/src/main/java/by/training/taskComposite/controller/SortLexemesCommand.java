package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.Library;

public class SortLexemesCommand implements Command {
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
     * Given symbol.
     */
    private char givenSymbol;

    /**
     * Initializing variables.
     *
     * @param newBar     NavigationBar object
     * @param newLibrary Library object
     * @param newPrinter ConsolePrinter object
     * @param newGivenSymbol char
     */
    public SortLexemesCommand(
            final NavigationBar newBar, final Library newLibrary,
            final ConsolePrinter newPrinter, final char newGivenSymbol) {
        this.bar = newBar;
        this.library = newLibrary;
        this.printer = newPrinter;
        this.givenSymbol = newGivenSymbol;
    }

    /**
     * Method calls to the method of Library object for sorting lexemes in
     * descending order of the number of occurrences of a given character
     * and then calls method of NavigationBar to continue.
     */
    @Override
    public void execute() {
        printer.printToconsole(library.sortLexemesByGivenSymbol(givenSymbol));
        bar.continuePage();
    }
}
