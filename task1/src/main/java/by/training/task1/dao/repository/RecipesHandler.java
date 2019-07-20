package by.training.task1.dao.repository;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.service.specification.Specification;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public final class RecipesHandler extends SubmissionPublisher<Recipe>
        implements Repository<Recipe>, Cloneable, Serializable {
    /**
     * Variable for keeping RecipesHandler instance.
     */
    private static final RecipesHandler SINGLE_INSTANCE;
    /**
     * List of recipes.
     */
    private List<Recipe> recipes;

    static {
        SINGLE_INSTANCE = new RecipesHandler();
    }

    private RecipesHandler() {
        recipes = new LinkedList<>();
    }

    /**
     * Get method.
     *
     * @return single instance of RecipesHandler
     */
    public static RecipesHandler getInstance() {
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
    public List<Recipe> readAll() {
        List<Recipe> copyList = new LinkedList<>(recipes);
        return copyList;
    }

    @Override
    public void add(final Recipe item) {
        recipes.add(item);
    }

    @Override
    public void remove(final Recipe item) {
        recipes.remove(item);
    }

    @Override
    public void update(final Recipe item) {
        submit(item);
    }

    @Override
    public List<Recipe> query(final Specification specification) {
        return null;
    }
}
