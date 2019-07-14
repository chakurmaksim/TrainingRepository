package by.training.task1.service.factory.certainvegetablefactory;

import by.training.task1.bean.entity.FruitVegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.service.factory.ValidatorFactory;
import by.training.task1.service.validator.VegetableValidator;

import static by.training.task1.bean.exception.NoSuchIngredientException.getAmountError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getNameError;
/**
 * Class for creating fruit vegetable instance.
 */
public final class FruitVegFactory {
    /**
     * Variable for keeping FruitVegFactory instance.
     */
    private static final FruitVegFactory SINGLE_INSTANCE;

    static {
        SINGLE_INSTANCE = new FruitVegFactory();
    }

    /**
     * constructor to prevent the creation of an object outside the class.
     */
    private FruitVegFactory() {
    }
    /**
     * Method to create fruit vegetable instance using passed parameters.
     * @param name vegetable name
     * @param kcal amount of kilocalories per 100 g of the vegetables.
     * @param proteins amount of proteins per 100 g of the vegetables.
     * @param fats amount of fats per 100 g of the vegetables.
     * @param carbohydrates amount of carbohydrates per 100 g of the vegetables.
     * @return bulbous vegetable instance
     * @throws NoSuchIngredientException when instance cannot pass validation
     */
    public FruitVegetable createCertainVeg(final String name,
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
        FruitVegetable fruitVegetable = new FruitVegetable(name);
        fruitVegetable.setKcalPer100g(kcal);
        fruitVegetable.setProteinsPer100g(proteins);
        fruitVegetable.setFatsPer100g(fats);
        fruitVegetable.setCarbohydratesPer100g(carbohydrates);
        return fruitVegetable;
    }

    /**
     * Get method.
     * @return single instance FruitVegFactory
     */
    public static FruitVegFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
