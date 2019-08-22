package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.ActionChooser;

public class OfferLanguageCommand implements Command {
    /**
     * NavigationBar variable.
     */
    private NavigationBar bar;
    /**
     * ActionChooser variable.
     */
    private ActionChooser chooser;
    /**
     * ConsolePrinter variable.
     */
    private ConsolePrinter printer;

    /**
     * Initializing variables.
     *
     * @param newBar NavigationBar object
     * @param newChooser ActionChooser object
     * @param newPrinter ConsolePrinter object
     */
    public OfferLanguageCommand(
            final NavigationBar newBar, final ActionChooser newChooser,
            final ConsolePrinter newPrinter) {
        this.bar = newBar;
        this.chooser = newChooser;
        this.printer = newPrinter;
    }

    /**
     * Method calls to the method of ActionChooser object for printing different
     * offers of languages and then calls next method of NavigationBar object.
     */
    @Override
    public void execute() {
        printer.printToconsole(chooser.offerLanguages());
        bar.chooseLanguage();
    }
}
