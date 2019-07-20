package by.training.task1.service.factory;

import by.training.task1.service.validator.RecipeValidator;
import by.training.task1.service.validator.VegetableValidator;

/**
 * Class that contains validator instances.
 */
public final class ValidatorFactory {
    /**
     * Variable for keeping ValidatorFactory instance.
     */
    public static final ValidatorFactory SINGLE_INSTANCE;

    static {
        SINGLE_INSTANCE = new ValidatorFactory();
    }

    private ValidatorFactory() {
    }

    /**
     * Declaration and instantiating VegetableValidator instance.
     */
    private final VegetableValidator vegetableValid = new VegetableValidator();
    /**
     * Declaration and instantiating RecipeValidator instance.
     */
    private final RecipeValidator recipeValid = new RecipeValidator();

    /**
     * Get method.
     *
     * @return single instance ValidatorFactory
     */
    public static ValidatorFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    /**
     * Get method.
     *
     * @return instance of VegetableValidator
     */
    public VegetableValidator getVegetableValid() {
        return vegetableValid;
    }

    /**
     * Get method.
     *
     * @return instance of RecipeValidator
     */
    public RecipeValidator getRecipeValid() {
        return recipeValid;
    }
}
