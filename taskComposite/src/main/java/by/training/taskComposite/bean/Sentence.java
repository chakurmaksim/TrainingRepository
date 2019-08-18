package by.training.taskComposite.bean;

public class Sentence extends TextComposite {
    /**
     * Delimiter is used to restore text from text components.
     */
    private static final String DELIMITER = " ";

    /**
     * Constructor for initializing delimiter in the class parent.
     */
    public Sentence() {
        super(DELIMITER);
    }
}
