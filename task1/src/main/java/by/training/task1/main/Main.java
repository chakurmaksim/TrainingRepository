package by.training.task1.main;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.entity.Salad;
import by.training.task1.controller.Chef;
import by.training.task1.dao.repository.RecipesHandler;
import by.training.task1.dao.repository.SaladsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * The Main class for starting programme.
 */
final class Main {
    /**
     * An object of this class is not created.
     */
    private Main() {

    }

    /**
     * The whole programme starts with the main method.
     *
     * @param args input parameters
     */
    public static void main(final String[] args) {
        Chef chef = new Chef();
        Logger logger = LogManager.getRootLogger();

        for (Salad salad : chef.createSaladList()) {
            logger.info(salad);
        }
        logger.info("------------------------------");

        for (Salad salad : chef.findSaladById(4)) {
            logger.info(salad);
        }
        logger.info("------------------------------");

        for (Salad salad
                : chef.findSaladByName("Vegetable salad with spinach")) {
            logger.info(salad);
        }
        logger.info("------------------------------");

        for (Salad salad
                : chef.findSaladByKcal(100, 150)) {
            logger.info(salad);
        }
        logger.info("------------------------------");

        for (Salad salad : chef.sortSaladsByWeight()) {
            logger.info(salad);
        }
        System.out.println("------------------------------");
        for (Salad salad : chef.sortSaladByKcalPer100g()) {
            logger.info(salad);
        }
        logger.info("------------------------------");

        chef.getKitchen().writeResultToFile(chef.sortSaladByKcalPer100g());

        chef.createSubscription();
        int changesNum = 2;
        Recipe recipe = RecipesHandler.getInstance().readAll().get(0);
        logger.info(recipe);
        for (Map.Entry<String, Integer> entry
                : recipe.getComposition().entrySet()) {
            entry.setValue(100);
        }
        logger.info(recipe);
        chef.changeRecipe(recipe);
        logger.info("------------------------------");

        recipe = RecipesHandler.getInstance().readAll().get(1);
        logger.info(recipe);
        for (Map.Entry<String, Integer> entry
                : recipe.getComposition().entrySet()) {
            entry.setValue(100);
        }
        logger.info(recipe);
        chef.changeRecipe(recipe);
        logger.info("------------------------------");

        while (chef.getKitchen().getCountChanges() != changesNum) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Salad salad : SaladsHandler.getInstance().readAll()) {
            logger.info(salad);
        }
        chef.closePublisher();
    }
}
