package by.training.task1.controller;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.entity.Salad;
import by.training.task1.dao.repository.RecipesHandler;
import by.training.task1.dao.repository.SaladsHandler;
import by.training.task1.service.action.Kitchen;
import by.training.task1.service.specification.*;

import java.util.List;

/**
 * Controller.
 */
public class Chef {
    /**
     * Variable for keeping Kitchen instance.
     */
    private Kitchen kitchen;

    /**
     * Constructor, where the kitchen variable is initialized.
     */
    public Chef() {
        kitchen = new Kitchen();
    }

    /**
     * Get method.
     *
     * @return Kitchen instance.
     */
    public Kitchen getKitchen() {
        return kitchen;
    }

    /**
     * This method starts the process of reading information from files and
     * placing ready-made salads in repository.
     *
     * @return list of ready-made salads
     */
    public List<Salad> createSaladList() {
        kitchen.produceSalads();
        return SaladsHandler.getInstance().readAll();
    }

    /**
     * This method finds salad by salad id.
     *
     * @param id salad id or order number
     * @return list of matching salads
     */
    public List<Salad> findSaladById(final long id) {
        return SaladsHandler.getInstance().query(new FindSaladById(id));
    }

    /**
     * This method finds salad by salad name.
     *
     * @param name salad name
     * @return list of matching salads
     */
    public List<Salad> findSaladByName(final String name) {
        return SaladsHandler.getInstance().query(new FindSaladByName(name));
    }

    /**
     * Method finds the salad according to the specified values ​​of calories.
     *
     * @param min minimum value of calories.
     * @param max maximum value of calories.
     * @return list of appropriate salads
     */
    public List<Salad> findSaladByKcal(final double min, final double max) {
        return SaladsHandler.getInstance().query(new FindSaladByKcal(min, max));
    }

    /**
     * This method sort salads by weight and then by kcal.
     *
     * @return list of sorting salads
     */
    public List<Salad> sortSaladsByWeight() {
        return SaladsHandler.getInstance().query(new SortSaladByWeight());
    }

    /**
     * This method sort salads by kcal per 100g and then by name.
     *
     * @return list of sorting salads
     */
    public List<Salad> sortSaladByKcalPer100g() {
        return SaladsHandler.getInstance().query(new SortSaladByKcalPer100g());
    }

    /**
     * Create subscription at SubmissionPublisher<Recipe>.
     */
    public void createSubscription() {
        RecipesHandler.getInstance().subscribe(kitchen);
    }

    /**
     * Change recipe composition or its name.
     *
     * @param recipe Recipe instance
     */
    public void changeRecipe(final Recipe recipe) {
        RecipesHandler.getInstance().update(recipe);
    }

    /**
     * Close SubmissionPublisher<Recipe>.
     */
    public void closePublisher() {
        RecipesHandler.getInstance().close();
    }


}
