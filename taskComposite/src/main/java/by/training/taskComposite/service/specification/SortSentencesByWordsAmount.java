package by.training.taskComposite.service.specification;

import by.training.task1.service.specification.SortSpecification;
import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Sentence;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;


public class SortSentencesByWordsAmount
        implements SortSpecification<Paragraph> {
    /**
     * Method to sort sentences by words amount.
     *
     * @param paragraphs list of components
     */
    @Override
    public void sortSpecifiedComparator(final List<Paragraph> paragraphs) {
        for (Paragraph paragraph : paragraphs) {
            List<Sentence> sentences = new LinkedList<>();
            paragraph.getTextComponentStream().forEach(
                    s -> sentences.add((Sentence) s));
            sentences.stream().forEach(s -> paragraph.remove(s));
            Comparator<Sentence> comparator = (s1, s2) ->
                    s1.getComponentsListSize() - s2.getComponentsListSize();
            Collections.sort(sentences, comparator);
            sentences.stream().forEach(s -> paragraph.add(s));
        }
    }
}
