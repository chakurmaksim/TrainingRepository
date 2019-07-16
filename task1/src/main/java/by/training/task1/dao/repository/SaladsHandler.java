package by.training.task1.dao.repository;

import by.training.task1.bean.entity.Salad;
import by.training.task1.service.specification.FindSpecification;
import by.training.task1.service.specification.SortSpecification;
import by.training.task1.service.specification.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Storage for the collection of salads.
 */
public final class SaladsHandler implements Repository<Salad>,
        Cloneable, Serializable {
    /**
     * Variable for keeping SaladsHandler instance.
     */
    private static final SaladsHandler SINGLE_INSTANCE;
    /**
     * List of prepared salads.
     */
    private final List<Salad> saladList;

    static {
        SINGLE_INSTANCE = new SaladsHandler();
    }

    private SaladsHandler() {
        saladList = new LinkedList<>();
    }

    /**
     * Get method.
     *
     * @return single instance of SaladsHandler
     */
    public static SaladsHandler getInstance() {
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
    public List<Salad> readAll() {
        List<Salad> copyList = new LinkedList<>(saladList);
        return copyList;
    }

    @Override
    public void add(final Salad item) {
        saladList.add(item);
    }

    @Override
    public void remove(final Salad item) {
        saladList.remove(item);
    }

    @Override
    public void update(final Salad item) {
    }

    @Override
    public List<Salad> query(final Specification specification) {
        List<Salad> requiredList = null;
        if (specification instanceof FindSpecification) {
            requiredList = new ArrayList<>();
            for (Salad salad : saladList) {
                if (((FindSpecification) specification).findSpecified(salad)) {
                    requiredList.add(salad);
                    break;
                }
            }
        }
        if (specification instanceof SortSpecification) {
            requiredList = new LinkedList<>(saladList);
            ((SortSpecification) specification).
                    sortSpecifiedComparator(requiredList);
        }
        return requiredList;
    }
}
