package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortSaladByWeight implements SortSpecification<Salad> {
    @Override
    public void sortSpecifiedComparator(List<Salad> entity) {
        Comparator<Salad> comparator = (s1, s2) -> s1.getWeight() - s2.getWeight();
        Collections.sort(entity, comparator);
    }
}
