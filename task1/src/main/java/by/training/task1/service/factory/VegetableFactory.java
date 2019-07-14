package by.training.task1.service.factory;

import by.training.task1.bean.entity.Vegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.coder.VegetableDecoder;
import by.training.task1.service.caloriescalc.CaloriesCalculator;
import by.training.task1.service.validator.VegetableValidator;

import java.util.Optional;

import static by.training.task1.bean.exception.NoSuchIngredientException.getContentError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getGroupError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getNameError;
import static by.training.task1.bean.exception.NoSuchIngredientException.getAmountError;

/**
 * Class for creating vegetable instance.
 */
public final class VegetableFactory {
    /**
     * Variable for keeping VegetableFactory instance.
     */
    private static final VegetableFactory SINGLE_INSTANCE;

    static {
        SINGLE_INSTANCE = new VegetableFactory();
    }

    /**
     * constructor to prevent the creation of an object outside the class.
     */
    private VegetableFactory() {
    }

    /**
     * Method to create vegetable instance from Json format string.
     *
     * @param rawVeg Json format string
     * @return Vegetable instance
     * @throws NoSuchIngredientException when instance cannot pass validation
     */
    public Vegetable createVegFromJson(final String rawVeg)
            throws NoSuchIngredientException {
        CoderFactory coderFactory = CoderFactory.getSingleInstance();
        VegetableDecoder vegDecoder = coderFactory.getVegetableDecoder();
        Optional<Vegetable> optionalVeg = vegDecoder.decodeVegetable(rawVeg);
        Vegetable vegetable = optionalVeg.orElseThrow(() ->
                new NoSuchIngredientException(getContentError()));
        return validateVeg(vegetable);
    }

    private Vegetable validateVeg(final Vegetable veg)
            throws NoSuchIngredientException {
        ValidatorFactory validFactory = ValidatorFactory.getSingleInstance();
        VegetableValidator vegValidator = validFactory.getVegetableValid();
        if (!vegValidator.validateGroupName(veg.getGroupName())) {
            throw new NoSuchIngredientException(getGroupError());
        }
        if (vegValidator.validateIsNameEmpty(veg.getVegName())) {
            throw new NoSuchIngredientException(getNameError());
        }
        if (vegValidator.validateNegCaloriesNum(veg.getKcalPer100g())) {
            double proteins = veg.getProteinsPer100g();
            double fats = veg.getFatsPer100g();
            double carbohydrates = veg.getCarbohydratesPer100g();
            if (vegValidator.validateNegNutrientNum(proteins)
                    || vegValidator.validateNegNutrientNum(fats)
                    || vegValidator.validateNegNutrientNum(carbohydrates)) {
                throw new NoSuchIngredientException(getAmountError());
            }
            int calories = CaloriesCalculator.calcCaloriesUseNutrients(proteins,
                    fats, carbohydrates);
            veg.setKcalPer100g(calories);
            return veg;
        }
        return veg;
    }

    /**
     * Get method.
     *
     * @return single instance VegetableFactory
     */
    public static VegetableFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
