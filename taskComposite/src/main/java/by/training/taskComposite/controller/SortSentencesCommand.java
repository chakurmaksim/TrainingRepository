package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.Library;

public class SortSentencesCommand implements Command {
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
     * @param newBar     NavigationBar object
     * @param newLibrary Library object
     * @param newPrinter ConsolePrinter object
     */
    public SortSentencesCommand(
            final NavigationBar newBar, final Library newLibrary,
            final ConsolePrinter newPrinter) {
        this.bar = newBar;
        this.library = newLibrary;
        printer = newPrinter;
    }

    /**
     * Method calls to the method of Library object for sorting sentences in a
     * paragraph by words amount and then calls method of NavigationBar object
     * to continue.
     */
    @Override
    public void execute() {
        printer.printToconsole(library.sortSentencesByWordsAmount());
        bar.continuePage();
    }
}
