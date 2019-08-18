package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.TextComponent;

public class TextParser extends AbstractDataParser {
    /**
     * Constructor for instantiating a successor of the TextParser object.
     */
    public TextParser() {
        super(new ParagraphParser());
    }

    /**
     * Method to parse the string into text.
     *
     * @param textComponent Text object
     * @param part string of the text
     */
    @Override
    public void parse(final TextComponent textComponent, final String part) {
        chain(textComponent, part);
    }
}
