package by.training.task1.service.factory.certainvegetablefactory;

import by.training.task1.bean.entity.LeafyVegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.service.factory.ValidatorFactory;
import by.training.task1.service.validator.VegetableValidator;

import static by.training.task1.bean.exception.NoSuchIngredientException.getAmountError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getNameError;

/**
 * Class for creating leafy vegetable instance.
 */
public final class LeafyVegFactory {
    /**
     * Variable for keeping LeafyVegFactory instance.
     */
    private static final LeafyVegFactory SINGLE_INSTANCE;

    static {
        SINGLE_INSTANCE = new LeafyVegFactory();
    }

    /**
     * constructor to prevent the creation of an object outside the class.
     */
    private LeafyVegFactory() {
    }
    /**
     * Method to create leafy vegetable instance using passed parameters.
     * @param name vegetable name
     * @param kcal amount of kilocalories per 100 g of the vegetables.
     * @param proteins amount of proteins per 100 g of the vegetables.
     * @param fats amount of fats per 100 g of the vegetables.
     * @param carbohydrates amount of carbohydrates per 100 g of the vegetables.
     * @return bulbous vegetable instance
     * @throws NoSuchIngredientException when instance cannot pass validation
     */
    public LeafyVegetable createCertainVeg(final String name,
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
        LeafyVegetable leafyVegetable = new LeafyVegetable(name);
        leafyVegetable.setKcalPer100g(kcal);
        leafyVegetable.setProteinsPer100g(proteins);
        leafyVegetable.setFatsPer100g(fats);
        leafyVegetable.setCarbohydratesPer100g(carbohydrates);
        return leafyVegetable;
    }

    /**
     * Get method.
     * @return single instance LeafyVegFactory
     */
    public static LeafyVegFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
