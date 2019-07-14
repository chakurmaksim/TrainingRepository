package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;

public class FindSaladById implements FindSpecification<Salad> {
    private final long id;

    public FindSaladById(final long id) {
        this.id = id;
    }

    @Override
    public boolean findSpecified(Salad entity) {
        return this.id == entity.getSaladID();
    }
}
