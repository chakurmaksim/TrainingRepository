package by.training.task1.service.factory;

import by.training.task1.bean.entity.Vegetable;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.service.coder.VegetableDecoder;
import by.training.task1.service.caloriescalc.CaloriesCalculator;
import by.training.task1.service.validator.VegetableValidator;

import java.util.Optional;

import static by.training.task1.bean.exception.NoSuchIngredientException.getContentError;
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
        return validateVeg(vegetable, rawVeg);
    }

    private Vegetable validateVeg(final Vegetable veg, final String rawVeg)
            throws NoSuchIngredientException {
        ValidatorFactory validFactory = ValidatorFactory.getSingleInstance();
        VegetableValidator vegValidator = validFactory.getVegetableValid();
        if (veg.getGroupName() == null || veg.getVegName() == null) {
            String msg = String.format("%s: %s", getContentError(), rawVeg);
            throw new NoSuchIngredientException(msg);
        }
        if (vegValidator.validateIsNameEmpty(veg.getVegName())) {
            String msg = String.format("%s: %s", getNameError(), rawVeg);
            throw new NoSuchIngredientException(msg);
        }
        if (vegValidator.validateNegCaloriesNum(veg.getKcalPer100g())) {
            double proteins = veg.getProteinsPer100g();
            double fats = veg.getFatsPer100g();
            double carbohydrates = veg.getCarbohydratesPer100g();
            if (vegValidator.validateNegNutrientNum(proteins)
                    || vegValidator.validateNegNutrientNum(fats)
                    || vegValidator.validateNegNutrientNum(carbohydrates)) {
                String msg = String.format("%s: %s", getAmountError(), rawVeg);
                throw new NoSuchIngredientException(msg);
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
