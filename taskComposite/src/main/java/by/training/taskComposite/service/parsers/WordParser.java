package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.*;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse string to words and punctuation marks.
 */
public class WordParser extends AbstractDataParser {
    /**
     * Regular expression for punctuation marks.
     */
    private static final String REGEX;
    /**
     * Declaring variable of the Pattern instance.
     */
    private Pattern pattern;

    static {
        REGEX = "[,:;'\"!\\.\\?-]";
    }

    /**
     * Constructor for instantiating a successor of the WordParser object.
     */
    public WordParser() {
        super(new SymbolParser());
        pattern = Pattern.compile(REGEX);
    }

    /**
     * Method to parse the string into words and punctuation marks.
     *
     * @param textComponent Lexeme object
     * @param part string of the lexeme
     */
    @Override
    public void parse(final TextComponent textComponent, final String part) {
        Matcher matcher = pattern.matcher(part);
        List<Integer> punctuationIndexes = new LinkedList<>();
        while (matcher.find()) {
            punctuationIndexes.add(matcher.start());
        }
        if (textComponent instanceof Lexeme) {
            Lexeme lexeme = (Lexeme) textComponent;
            if (punctuationIndexes.size() == 0) {
                Word word = new Word();
                lexeme.add(word);
                chain(word, part);
                return;
            }
            int previousInd = 0;
            for (int i = 0; i < punctuationIndexes.size(); i++) {
                int punctuationInd = punctuationIndexes.get(i);
                if (part.substring(previousInd, punctuationInd).isEmpty()) {
                    previousInd = punctuationInd + 1;
                    if (i == punctuationIndexes.size() - 1) {
                        if (!part.substring(previousInd).isEmpty()) {
                            addMidPunctuation(lexeme, part, punctuationInd);
                            addLastWord(lexeme, part, previousInd);
                        } else {
                            addLastPunctuation(lexeme, part, punctuationInd);
                        }
                    } else {
                        addMidPunctuation(lexeme, part, punctuationInd);
                    }
                } else {
                    Word word = new Word();
                    lexeme.add(word);
                    chain(word, part.substring(previousInd, punctuationInd));
                    previousInd = punctuationInd + 1;
                    if (i == punctuationIndexes.size() - 1) {
                        if (!part.substring(previousInd).isEmpty()) {
                            addMidPunctuation(lexeme, part, punctuationInd);
                            addLastWord(lexeme, part, previousInd);
                        } else {
                            addLastPunctuation(lexeme, part, punctuationInd);
                        }
                    } else {
                        addMidPunctuation(lexeme, part, punctuationInd);
                    }
                }
            }
        }
    }

    private void addLastWord(
            final Lexeme lexeme, final String part, final int previousInd) {
        Word word = new Word();
        lexeme.add(word);
        chain(word, part.substring(previousInd));
    }

    private void addMidPunctuation(
            final Lexeme lexeme, final String part, final int punctuationInd) {
        Punctuation punctuation = new Punctuation();
        lexeme.add(punctuation);
        chain(punctuation, Character.toString(part.charAt(punctuationInd)));
    }

    private void addLastPunctuation(
            final Lexeme lexeme, final String part, final int punctuationInd) {
        Punctuation punctuation = new Punctuation();
        lexeme.add(punctuation);
        chain(punctuation, part.substring(punctuationInd));
    }
}
