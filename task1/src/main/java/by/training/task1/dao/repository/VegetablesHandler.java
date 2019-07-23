package by.training.task1.dao.repository;

import by.training.task1.bean.entity.Vegetable;
import by.training.task1.service.specification.Specification;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


/**
 * Storage for the collection of vegetables.
 */
public final class VegetablesHandler implements Repository<Vegetable>,
        Cloneable, Serializable {
    /**
     * Variable for keeping VegetablesHandler instance.
     */
    private static final VegetablesHandler SINGLE_INSTANCE;
    /**
     * Set of vegetables.
     */
    private final Set<Vegetable> vegetableSet;

    static {
        SINGLE_INSTANCE = new VegetablesHandler();
    }

    private VegetablesHandler() {
        vegetableSet = new HashSet<>();
    }

    /**
     * Get method.
     *
     * @return single instance of VegetablesHandler
     */
    public static VegetablesHandler getInstance() {
        return SINGLE_INSTANCE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return SINGLE_INSTANCE;
    }

    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }

    @Override
    public List<Vegetable> readAll() {
        List<Vegetable> copyList = new ArrayList<>(vegetableSet);
        return copyList;
    }

    @Override
    public void add(final Vegetable item) {
        vegetableSet.add(item);
    }

    @Override
    public void remove(final Vegetable item) {
        vegetableSet.remove(item);
    }

    @Override
    public void update(final Vegetable item) {
    }

    @Override
    public List<Vegetable> query(final Specification specification) {
        return null;
    }
}
