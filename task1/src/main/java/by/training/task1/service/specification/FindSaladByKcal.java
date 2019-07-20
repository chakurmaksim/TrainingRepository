package by.training.task1.service.specification;

import by.training.task1.bean.entity.Salad;

public class FindSaladByKcal implements FindSpecification<Salad> {
    /**
     * Minimum content of kilocalories.
     */
    private double minKcal;
    /**
     * Maximum content of kilocalories.
     */
    private double maxKcal;

    /**
     * Constructor with two parameters.
     *
     * @param min minimum content of kilocalories.
     * @param max maximum content of kilocalories.
     */
    public FindSaladByKcal(final double min, final double max) {
        this.minKcal = min;
        this.maxKcal = max;
    }

    /**
     * Method to find salads with specified content of kilocalories.
     *
     * @param entity Salad entity
     * @return true if salad satisfy specified requirements
     */
    @Override
    public boolean findSpecified(final Salad entity) {
        return entity.getKcal() >= minKcal && entity.getKcal() <= maxKcal;
    }
}
