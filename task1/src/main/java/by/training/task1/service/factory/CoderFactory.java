package by.training.task1.service.factory;

import by.training.task1.service.coder.RecipeDecoder;
import by.training.task1.service.coder.RecipeEncoder;
import by.training.task1.service.coder.VegetableDecoder;
import by.training.task1.service.coder.VegetableEncoder;

/**
 * Class for keeping coders.
 */
public final class CoderFactory {
    /**
     * Declaration and initializing CoderFactory variable.
     */
    public static final CoderFactory SINGLE_INSTANCE = new CoderFactory();
    /**
     * Declaration and initializing RecipeEncoder variable.
     */
    private final RecipeEncoder recipeEncoder = new RecipeEncoder();
    /**
     * Declaration and initializing RecipeDecoder variable.
     */
    private final RecipeDecoder recipeDecoder = new RecipeDecoder();
    /**
     * Declaration and initializing VegetableEncoder variable.
     */
    private final VegetableEncoder vegetableEncoder = new VegetableEncoder();
    /**
     * Declaration and initializing VegetableDecoder variable.
     */
    private final VegetableDecoder vegetableDecoder = new VegetableDecoder();

    private CoderFactory() {
    }

    /**
     * Get method.
     *
     * @return CoderFactory instance
     */
    public static CoderFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    /**
     * Get method.
     *
     * @return RecipeEncoder instance
     */
    public RecipeEncoder getRecipeEncoder() {
        return recipeEncoder;
    }

    /**
     * Get method.
     *
     * @return RecipeDecoder instance
     */
    public RecipeDecoder getRecipeDecoder() {
        return recipeDecoder;
    }

    /**
     * Get method.
     *
     * @return VegetableEncoder instance
     */
    public VegetableEncoder getVegetableEncoder() {
        return vegetableEncoder;
    }

    /**
     * Get method.
     *
     * @return VegetableDecoder instance
     */
    public VegetableDecoder getVegetableDecoder() {
        return vegetableDecoder;
    }
}
