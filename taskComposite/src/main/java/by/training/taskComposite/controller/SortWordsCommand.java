package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.Library;

public class SortWordsCommand implements Command {
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
    public SortWordsCommand(
            final NavigationBar newBar, final Library newLibrary,
            final ConsolePrinter newPrinter) {
        this.bar = newBar;
        this.library = newLibrary;
        this.printer = newPrinter;
    }

    /**
     * Method calls to the method of Library object for sorting words in a
     * sentence by length and then calls method of NavigationBar to continue.
     */
    @Override
    public void execute() {
        printer.printToconsole(library.sortWordsByLength());
        bar.continuePage();
    }
}
