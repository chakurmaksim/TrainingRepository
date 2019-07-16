package by.training.task1.controller;

import by.training.task1.bean.entity.Salad;
import by.training.task1.dao.repository.SaladsHandler;
import by.training.task1.service.action.Kitchen;
import by.training.task1.service.specification.Specification;
import by.training.task1.service.specification.FindSaladById;
import by.training.task1.service.specification.FindSaladByName;
import by.training.task1.service.specification.SortSaladByWeight;
import by.training.task1.service.specification.SortSaladByKcalPer100g;

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
     * This method starts the process of reading information from files and
     * placing ready-made salads in repository.
     * @return list of ready-made salads
     */
    public List<Salad> createSaladList() {
        kitchen.produceSalads();
        return SaladsHandler.getInstance().readAll();
    }

    /**
     * This method finds salad by salad id.
     * @param id salad id or order number
     * @return list of matching salads
     */
    public List<Salad> findSaladById(final long id) {
        Specification specification = new FindSaladById(id);
        return SaladsHandler.getInstance().query(specification);
    }

    /**
     * This method finds salad by salad name.
     * @param name salad name
     * @return list of matching salads
     */
    public List<Salad> findSaladByName(final String name) {
        Specification specification = new FindSaladByName(name);
        return SaladsHandler.getInstance().query(specification);
    }

    /**
     * This method sort salads by weight and then by kcal.
     * @return list of sorting salads
     */
    public List<Salad> sortSaladsByWeight() {
        Specification specification = new SortSaladByWeight();
        return SaladsHandler.getInstance().query(specification);
    }

    /**
     * This method sort salads by kcal per 100g and then by name.
     * @return list of sorting salads
     */
    public List<Salad> sortSaladByKcalPer100g() {
        Specification specification = new SortSaladByKcalPer100g();
        return SaladsHandler.getInstance().query(specification);
    }
}
