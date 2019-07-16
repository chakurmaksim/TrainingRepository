package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;

public class FindSaladById implements FindSpecification<Salad> {
    /**
     * salad id or order number.
     */
    private final long id;

    /**
     * Constructor with one parameter.
     *
     * @param newId salad id or order number.
     */
    public FindSaladById(final long newId) {
        this.id = newId;
    }

    /**
     * Method to compare salads id.
     *
     * @param entity entity
     * @return true if match by id found or else false
     */
    @Override
    public boolean findSpecified(final Salad entity) {
        return this.id == entity.getSaladID();
    }
}
