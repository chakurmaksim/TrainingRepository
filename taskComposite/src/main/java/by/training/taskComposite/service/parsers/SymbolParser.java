package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Punctuation;
import by.training.taskComposite.bean.Symbol;
import by.training.taskComposite.bean.TextComponent;
import by.training.taskComposite.bean.Word;

public class SymbolParser extends AbstractDataParser {
    /**
     * Regular expression for split words and punctuation.
     */
    private static final String REGEX;

    static {
        REGEX = "";
    }

    /**
     * Method to parse the string to symbols.
     *
     * @param textComponent word or punctuation
     * @param part string of the word or punctuation
     */
    @Override
    public void parse(final TextComponent textComponent, final String part) {
        String[] symbolsArr = part.split(REGEX);
        if (textComponent instanceof Word) {
            Word word = (Word) textComponent;
            for (String symbolStr : symbolsArr) {
                if (!symbolStr.equals("\n")) {
                    word.add(new Symbol(symbolStr.toCharArray()[0]));
                }
            }
        }
        if (textComponent instanceof Punctuation) {
            Punctuation punctuationMark = (Punctuation) textComponent;
            for (String symbolStr : symbolsArr) {
                if (!symbolStr.equals("\n")) {
                    punctuationMark.add(new Symbol(symbolStr.toCharArray()[0]));
                }
            }
        }
    }
}
