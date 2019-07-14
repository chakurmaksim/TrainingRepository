package by.training.task1.service.factory.certainvegetablefactory;

import by.training.task1.bean.entity.RootVegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.service.factory.ValidatorFactory;
import by.training.task1.service.validator.VegetableValidator;

import static by.training.task1.bean.exception.NoSuchIngredientException.getAmountError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getNameError;

/**
 * Class for creating root vegetable instance.
 */
public final class RootVegFactory {
    /**
     * Variable for keeping RootVegFactory instance.
     */
    private static final RootVegFactory SINGLE_INSTANCE;

    static {
        SINGLE_INSTANCE = new RootVegFactory();
    }

    /**
     * constructor to prevent the creation of an object outside the class.
     */
    private RootVegFactory() {
    }

    /**
     * Method to create root vegetable instance using passed parameters.
     *
     * @param name          vegetable name
     * @param kcal          amount of kilocalories per 100 g of the vegetables.
     * @param proteins      amount of proteins per 100 g of the vegetables.
     * @param fats          amount of fats per 100 g of the vegetables.
     * @param carbohydrates amount of carbohydrates per 100 g of the vegetables.
     * @return bulbous vegetable instance
     * @throws NoSuchIngredientException when instance cannot pass validation
     */
    public RootVegetable createCertainVeg(final String name,
                                          final int kcal,
                                          final double proteins,
                                          final double fats,
                                          final double carbohydrates)
            throws NoSuchIngredientException {
        ValidatorFactory validFactory = ValidatorFactory.getSingleInstance();
        VegetableValidator vegValidator = validFactory.getVegetableValid();
        if (vegValidator.validateIsNameEmpty(name)) {
            throw new NoSuchIngredientException(getNameError());
        }
        if (name == null || vegValidator.validateNegCaloriesNum(kcal)
                || vegValidator.validateNegNutrientNum(proteins)
                || vegValidator.validateNegNutrientNum(fats)
                || vegValidator.validateNegNutrientNum(carbohydrates)) {
            throw new NoSuchIngredientException(getAmountError());
        }
        RootVegetable rootVegetable = new RootVegetable(name);
        rootVegetable.setKcalPer100g(kcal);
        rootVegetable.setProteinsPer100g(proteins);
        rootVegetable.setFatsPer100g(fats);
        rootVegetable.setCarbohydratesPer100g(carbohydrates);
        return rootVegetable;
    }

    /**
     * Get method.
     *
     * @return single instance RootVegFactory
     */
    public static RootVegFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
