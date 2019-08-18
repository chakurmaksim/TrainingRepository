package by.training.taskComposite.bean;

public class Text extends TextComposite {
    /**
     * Delimiter is used to restore text from text components.
     */
    private static final String DELIMITER = "\r\n\t";

    /**
     * Constructor for initializing delimiter in the class parent.
     */
    public Text() {
        super(DELIMITER);
    }

    /**
     * Method to restore text from components.
     *
     * @return strings of the text
     */
    @Override
    public String concatenate() {
        return "\t" + super.concatenate();
    }
}
