package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortSaladByKcalPer100g implements SortSpecification<Salad> {
    /**
     * Method to sort the list of vegetables by kcal per 100g and then by name.
     * @param entity list of entities
     */
    @Override
    public void sortSpecifiedComparator(final List<Salad> entity) {
        Comparator<Salad> comparatorByKcal = (s1, s2) ->
                Double.compare(s1.getKcalPer100g(), s2.getKcalPer100g());
        Comparator<Salad> comparatorByName = (s1, s2) ->
                s1.getName().compareTo(s2.getName());
        Collections.sort(entity,
                comparatorByKcal.thenComparing(comparatorByName));
    }
}
