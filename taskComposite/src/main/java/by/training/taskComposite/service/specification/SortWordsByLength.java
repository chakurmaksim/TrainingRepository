package by.training.taskComposite.service.specification;

import by.training.task1.service.specification.SortSpecification;
import by.training.taskComposite.bean.Lexeme;
import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Sentence;
import by.training.taskComposite.bean.TextComponent;
import by.training.taskComposite.bean.Word;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Optional;

public class SortWordsByLength implements SortSpecification<Paragraph> {
    /**
     * Method to sort sentences by words length.
     *
     * @param paragraphs list of components
     */
    @Override
    public void sortSpecifiedComparator(final List<Paragraph> paragraphs) {
        for (Paragraph paragraph : paragraphs) {
            paragraph.getTextComponentStream().forEach(
                    x -> sortWords((Sentence) x));
        }
    }

    private void sortWords(final Sentence sentence) {
        List<Lexeme> lexemes = new LinkedList<>();
        sentence.getTextComponentStream().forEach(l -> lexemes.add((Lexeme) l));
        lexemes.stream().forEach(l -> sentence.remove(l));
        Comparator<Lexeme> comparator = (s1, s2) -> {
            Optional<TextComponent> first = s1.getTextComponentStream().filter(
                    w -> w instanceof Word).findFirst();
            Optional<TextComponent> second = s2.getTextComponentStream().filter(
                    w -> w instanceof Word).findFirst();
            Word firstWord = (Word) first.orElse(new Word());
            Word secondWord = (Word) second.orElse(new Word());
            int firstWordLength = firstWord.concatenate().length();
            int secondWordLength = secondWord.concatenate().length();
            int res = firstWordLength - secondWordLength;
            return res;
        };
        Collections.sort(lexemes, comparator);
        lexemes.stream().forEach(l -> sentence.add(l));
    }
}
