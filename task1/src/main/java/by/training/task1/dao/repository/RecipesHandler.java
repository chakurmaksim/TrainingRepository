package by.training.task1.dao.repository;

import by.training.task1.bean.entity.Recipe;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public final class RecipesHandler implements Cloneable, Serializable {
    private static final RecipesHandler SINGLE_INSTANCE;
    private Set<Recipe> recipes;

    static {
        SINGLE_INSTANCE = new RecipesHandler();
    }

    private RecipesHandler() {
        recipes = new HashSet<>();
    }

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

    public Set<Recipe> getRecipes() {
        return recipes;
    }
}
