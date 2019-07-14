package by.training.task1.dao.repository;

import by.training.task1.bean.entity.Vegetable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class VegetablesHandler implements Cloneable, Serializable {
    private static final VegetablesHandler SINGLE_INSTANCE;
    private final Set<Vegetable> vegetableSet;
    static {
        SINGLE_INSTANCE = new VegetablesHandler();
    }

    private VegetablesHandler() {
        vegetableSet = new HashSet<>();
    }

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

    public Set<Vegetable> getVegetableSet() {
        return vegetableSet;
    }
}
