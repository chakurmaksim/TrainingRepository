package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;

public class FindSaladByName implements FindSpecification<Salad> {
    /**
     * Salad name.
     */
    private final String name;

    /**
     * Constructor with one parameter.
     *
     * @param newName salad name
     */
    public FindSaladByName(final String newName) {
        this.name = newName;
    }

    /**
     * Method to compare salads name.
     *
     * @param entity Salad entity
     * @return true if names are equals
     */
    @Override
    public boolean findSpecified(final Salad entity) {
        return this.name.equalsIgnoreCase(entity.getName());
    }
}
