package by.training.taskComposite.controller;

import by.training.taskComposite.service.action.ActionChooser;

public class ChooseLanguageCommand implements Command {
    /**
     * NavigationBar variable.
     */
    private NavigationBar bar;
    /**
     * ActionChooser variable.
     */
    private ActionChooser chooser;
    /**
     * User input.
     */
    private int userInput;

    /**
     * Initializing variables.
     *
     * @param newBar       NavigationBar object
     * @param newChooser   ActionChooser object
     * @param newUserInput user input the type of int
     */
    public ChooseLanguageCommand(
            final NavigationBar newBar, final ActionChooser newChooser,
            final int newUserInput) {
        this.bar = newBar;
        this.chooser = newChooser;
        this.userInput = newUserInput;

    }

    /**
     * Method calls to the method of ActionChooser object for choosing
     * language of the navigation menu and then calls next method
     * of NavigationBar object.
     */
    @Override
    public void execute() {
        chooser.chooseLanguage(userInput);
        bar.startPage();
    }
}
