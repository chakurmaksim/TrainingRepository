package by.training.task1.service.factory.certainvegetablefactory;

import by.training.task1.bean.entity.BeanVegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.service.factory.ValidatorFactory;
import by.training.task1.service.validator.VegetableValidator;

import static by.training.task1.bean.exception.NoSuchIngredientException.getAmountError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getNameError;

/**
 * Class for creating bean vegetable instance.
 */
public final class BeanVegFactory {
    /**
     * Variable for keeping BeanVegFactory instance.
     */
    private static final BeanVegFactory SINGLE_INSTANCE;
    static {
        SINGLE_INSTANCE = new BeanVegFactory();
    }
    /**
     * constructor to prevent the creation of an object outside the class.
     */
    private BeanVegFactory() {
    }
    /**
     * Method to create bean vegetable instance using passed parameters.
     * @param name vegetable name
     * @param kcal amount of kilocalories per 100 g of the vegetables.
     * @param proteins amount of proteins per 100 g of the vegetables.
     * @param fats amount of fats per 100 g of the vegetables.
     * @param carbohydrates amount of carbohydrates per 100 g of the vegetables.
     * @return bulbous vegetable instance
     * @throws NoSuchIngredientException when instance cannot pass validation
     */
    public BeanVegetable createCertainVeg(final String name,
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
        BeanVegetable beanVegetable = new BeanVegetable(name);
        beanVegetable.setKcalPer100g(kcal);
        beanVegetable.setProteinsPer100g(proteins);
        beanVegetable.setFatsPer100g(fats);
        beanVegetable.setCarbohydratesPer100g(carbohydrates);
        return beanVegetable;
    }

    /**
     * Get method.
     * @return single instance BeanVegFactory
     */
    public static BeanVegFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
