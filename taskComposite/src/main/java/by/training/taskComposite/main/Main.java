package by.training.taskComposite.main;

import by.training.taskComposite.controller.NavigationBar;

public final class Main {
    private Main() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        NavigationBar nav = new NavigationBar();
        nav.startPage();
    }
}
