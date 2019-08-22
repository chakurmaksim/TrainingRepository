package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.TextComponent;

public abstract class AbstractDataParser {
    /**
     * Declaring a successor variable.
     */
    protected AbstractDataParser successor;

    /**
     * Initializing a successor variable.
     *
     * @param newSuccessor particular parser object
     */
    public AbstractDataParser(final AbstractDataParser newSuccessor) {
        successor = newSuccessor;
    }

    /**
     * Constructor with no arguments for creating a parser object.
     */
    public AbstractDataParser() {
    }

    /**
     * Method to parse a string into particular object.
     *
     * @param component object of the TextComponent.
     * @param part      of a string
     */
    public abstract void parse(TextComponent component, String part);

    /**
     * Method invokes next parse method of a TextComponent object
     * that is a successor of this.
     *
     * @param component TextComponent object
     * @param part      part of a text the type of String
     */
    public void chain(final TextComponent component, final String part) {
        successor.parse(component, part);
    }

    /**
     * Get a parser object that would be a successor of this.
     *
     * @return link to the successor parser
     */
    public AbstractDataParser getSuccessor() {
        return successor;
    }

    /**
     * Set a parser object that would be a successor of this.
     *
     * @param newSuccessor particular parser object
     */
    public void setSuccessor(final AbstractDataParser newSuccessor) {
        successor = newSuccessor;
    }
}
