package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Lexeme;
import by.training.taskComposite.bean.Sentence;
import by.training.taskComposite.bean.TextComponent;

public class LexemeParser extends AbstractDataParser {
    /**
     * Regular expression to parse lexemes.
     */
    private static final String REGEX;

    static {
        REGEX = " ";
    }

    /**
     * Constructor for instantiating a successor of the LexemeParser object.
     */
    public LexemeParser() {
        super(new WordParser());
    }

    /**
     * Method to parse the string to lexemes.
     *
     * @param component Sentence object
     * @param part      string of the sentence
     */
    @Override
    public void parse(final TextComponent component, final String part) {
        String[] lexemesStr = part.split(REGEX);
        if (component instanceof Sentence) {
            Sentence sentence = (Sentence) component;
            for (String lexemeStr : lexemesStr) {
                if (!lexemeStr.isEmpty()) {
                    Lexeme lexeme = new Lexeme();
                    sentence.add(lexeme);
                    chain(lexeme, lexemeStr);
                }
            }
        }
    }
}
