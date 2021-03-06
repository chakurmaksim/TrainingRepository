package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortSaladByWeight implements SortSpecification<Salad> {

    /**
     * Method to sort the list of vegetables by weight and then by kcal.
     * @param entity list of entities
     */
    @Override
    public void sortSpecifiedComparator(final List<Salad> entity) {
        Comparator<Salad> comparatorByWeight = (s1, s2) ->
                s1.getWeight() - s2.getWeight();
        Comparator<Salad> comparatorByKcal = (s1, s2) ->
                Double.compare(s1.getKcal(), s2.getKcal());
        Collections.sort(entity,
                comparatorByWeight.thenComparing(comparatorByKcal));
    }
}
