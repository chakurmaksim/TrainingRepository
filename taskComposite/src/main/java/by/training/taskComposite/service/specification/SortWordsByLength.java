package by.training.taskComposite.service.specification;

import by.training.task1.service.specification.SortSpecification;
import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Sentence;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SortWordsByLength implements SortSpecification<Paragraph> {

    @Override
    public void sortSpecifiedComparator(final List<Paragraph> paragraphs) {
        for (Paragraph paragraph : paragraphs) {
            List<Sentence> sentences = new LinkedList<>();
            paragraph.getTextComponentStream().forEach(
                    x -> sentences.add((Sentence) x));
            sentences.stream().forEach(x -> paragraph.remove(x));
            sortSentenceByWordLength(sentences);
            sentences.stream().forEach(x -> paragraph.add(x));
        }
    }

    private void sortSentenceByWordLength(final List<Sentence> sentences) {
        Comparator<Sentence> comparator = (s1, s2) -> s1.getComponentsListSize()
                - s2.getComponentsListSize();
        Collections.sort(sentences, comparator);
    }
}
