package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Text;
import by.training.taskComposite.bean.TextComponent;

public class ParagraphParser extends AbstractDataParser {
    /**
     * Regular expression for paragraphs.
     */
    private static final String REGEX;

    static {
        REGEX = "(?m)(?=^\\s{3,4})";
    }

    /**
     * Constructor for instantiating a successor of the ParagraphParser object.
     */
    public ParagraphParser() {
        super(new SentenceParser());
    }

    /**
     * Method to parse the string to paragraphs.
     *
     * @param textComponent Text object
     * @param part          string of the text
     */
    @Override
    public void parse(final TextComponent textComponent, final String part) {
        String[] paragraphsArr = part.split(REGEX);
        if (textComponent instanceof Text) {
            Text text = (Text) textComponent;
            for (String paragraphStr : paragraphsArr) {
                Paragraph paragraph = new Paragraph();
                text.add(paragraph);
                chain(paragraph, paragraphStr.trim());
            }
        }
    }
}
