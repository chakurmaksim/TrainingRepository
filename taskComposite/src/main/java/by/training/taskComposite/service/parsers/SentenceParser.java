package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Sentence;
import by.training.taskComposite.bean.TextComponent;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends AbstractDataParser {
    /**
     * Regular expression to parse sentences.
     */
    private static final String REGEX;
    /**
     * Declaring variable of the Pattern instance.
     */
    private Pattern pattern;

    static {
        REGEX = "\\.{1,3}|\\?!|\\?|!";
    }

    /**
     * Constructor for instantiating a successor of the SentenceParser object.
     */
    public SentenceParser() {
        super(new LexemeParser());
        pattern = Pattern.compile(REGEX);
    }

    /**
     * Method to parse the string into sentences.
     *
     * @param textComponent Paragraph object
     * @param part string of the paragraph
     */
    @Override
    public void parse(final TextComponent textComponent, final String part) {
        Matcher matcher = pattern.matcher(part);
        List<Integer> punctuationInd = new LinkedList<>();
        while (matcher.find()) {
            punctuationInd.add(matcher.end());
        }
        if (textComponent instanceof Paragraph) {
            Paragraph paragraph = (Paragraph) textComponent;
            int previousInd = 0;
            for (int index : punctuationInd) {
                Sentence sentence = new Sentence();
                paragraph.add(sentence);
                chain(sentence, part.substring(previousInd, index));
                previousInd = index;
            }
        }
    }
}
