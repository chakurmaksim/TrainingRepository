package by.training.task1.service.factory.certainvegetablefactory;

import by.training.task1.bean.entity.BulbousVegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.service.factory.ValidatorFactory;
import by.training.task1.service.validator.VegetableValidator;

import static by.training.task1.bean.exception.NoSuchIngredientException.getAmountError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getNameError;

/**
 * Class for creating bulbous vegetable instance.
 */
public final class BulbousVegFactory {
    /**
     * Variable for keeping BulbousVegFactory instance.
     */
    private static final BulbousVegFactory SINGLE_INSTANCE;

    static {
        SINGLE_INSTANCE = new BulbousVegFactory();
    }

    /**
     * constructor to prevent the creation of an object outside the class.
     */
    private BulbousVegFactory() {
    }

    /**
     * Method to create bulbous vegetable instance using passed parameters.
     *
     * @param name          vegetable name
     * @param kcal          amount of kilocalories per 100 g of the vegetables.
     * @param proteins      amount of proteins per 100 g of the vegetables.
     * @param fats          amount of fats per 100 g of the vegetables.
     * @param carbohydrates amount of carbohydrates per 100 g of the vegetables.
     * @return bulbous vegetable instance
     * @throws NoSuchIngredientException when instance cannot pass validation
     */
    public BulbousVegetable createCertainVeg(final String name,
                                             final int kcal,
                                             final double proteins,
                                             final double fats,
                                             final double carbohydrates)
            throws NoSuchIngredientException {
        ValidatorFactory validFactory = ValidatorFactory.getSingleInstance();
        VegetableValidator vegValidator = validFactory.getVegetableValid();
        if (name == null || vegValidator.validateIsNameEmpty(name)) {
            throw new NoSuchIngredientException(getNameError());
        }
        if (vegValidator.validateNegCaloriesNum(kcal)
                || vegValidator.validateNegNutrientNum(proteins)
                || vegValidator.validateNegNutrientNum(fats)
                || vegValidator.validateNegNutrientNum(carbohydrates)) {
            throw new NoSuchIngredientException(getAmountError());
        }
        BulbousVegetable bulbousVegetable = new BulbousVegetable(name);
        bulbousVegetable.setKcalPer100g(kcal);
        bulbousVegetable.setProteinsPer100g(proteins);
        bulbousVegetable.setFatsPer100g(fats);
        bulbousVegetable.setCarbohydratesPer100g(carbohydrates);
        return bulbousVegetable;
    }

    /**
     * Get method.
     * @return single instance BulbousVegFactory
     */
    public static BulbousVegFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
