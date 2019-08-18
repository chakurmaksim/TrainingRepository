package by.training.taskComposite.service.specification;

import by.training.task1.service.specification.SortSpecification;
import by.training.taskComposite.bean.Paragraph;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortParagraphs implements SortSpecification<Paragraph> {

    @Override
    public void sortSpecifiedComparator(final List<Paragraph> newList) {
        Comparator<Paragraph> comparator = (s1, s2) ->
                s1.getComponentsListSize() - s2.getComponentsListSize();
        Collections.sort(newList, comparator);
    }
}
