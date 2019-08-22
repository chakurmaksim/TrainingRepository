package by.training.taskComposite.service.specification;

import by.training.task1.service.specification.SortSpecification;
import by.training.taskComposite.bean.Lexeme;
import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Sentence;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SortLexemesBySymbols implements SortSpecification<Paragraph> {
    /**
     * Given symbol.
     */
    private char givenSymb;

    /**
     * Initialize a given symbol.
     *
     * @param newGivenSymb char
     */
    public SortLexemesBySymbols(final char newGivenSymb) {
        this.givenSymb = newGivenSymb;
    }

    /**
     * Sort lexemes in the text in descending order of the number of
     * occurrences of a given character, and in case of equality -
     * alphabetically.
     *
     * @param paragraphs list of components
     */
    @Override
    public void sortSpecifiedComparator(final List<Paragraph> paragraphs) {
        List<Paragraph> paragraphList = new LinkedList<>();
        Paragraph singleParagraph = new Paragraph();
        for (Paragraph par : paragraphs) {
            paragraphList.add(par);
            for (int i = 0; i < par.getComponentsListSize(); i++) {
                singleParagraph.add(par.getChild(i));
            }
        }
        paragraphList.stream().forEach(p -> paragraphs.remove(p));
        paragraphs.add(singleParagraph);
        concatSentences(singleParagraph);
    }

    private void concatSentences(final Paragraph singleParagraph) {
        List<Sentence> sentenceList = new LinkedList<>();
        singleParagraph.getTextComponentStream().forEach(s ->
                sentenceList.add((Sentence) s));
        Sentence singleSentence = new Sentence();
        for (Sentence sent : sentenceList) {
            for (int i = 0; i < sent.getComponentsListSize(); i++) {
                singleSentence.add(sent.getChild(i));
            }
        }
        sentenceList.stream().forEach(s -> singleParagraph.remove(s));
        singleParagraph.add(singleSentence);
        sortLexemes(singleSentence);
    }

    private void sortLexemes(final Sentence singleSentence) {
        List<Lexeme> lexemeList = new LinkedList<>();
        singleSentence.getTextComponentStream().forEach(
                l -> lexemeList.add((Lexeme) l));
        lexemeList.stream().forEach(l -> singleSentence.remove(l));
        Comparator<Lexeme> comparator = (l1, l2) -> {
            String first = l1.concatenate();
            String second = l2.concatenate();
            int firstCount = 0;
            int secondCount = 0;
            for (int i = 0; i < first.length(); i++) {
                if (givenSymb == first.charAt(i)) {
                    firstCount++;
                }
            }
            for (int i = 0; i < second.length(); i++) {
                if (givenSymb == second.charAt(i)) {
                    secondCount++;
                }
            }
            int res = firstCount - secondCount;
            return res == 0 ? first.compareTo(second) : res;
        };
        Collections.sort(lexemeList, comparator);
        lexemeList.stream().forEach(l -> singleSentence.add(l));
    }
}
