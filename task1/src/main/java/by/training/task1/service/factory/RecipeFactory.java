package by.training.task1.service.factory;

import by.training.task1.service.coder.RecipeDecoder;
import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.exception.RecipeSyntaxException;
import by.training.task1.service.validator.RecipeValidator;

import java.util.Map;
import java.util.Optional;

import static by.training.task1.bean.exception.RecipeSyntaxException.getContentError;
import static by.training.task1.bean.exception.RecipeSyntaxException.getNameError;
import static by.training.task1.bean.exception.RecipeSyntaxException.getAmountError;

/**
 * Class for creating Recipe instance.
 */
public final class RecipeFactory {
    /**
     * Variable for keeping RecipeFactory instance.
     */
    private static final RecipeFactory SINGLE_INSTANCE;
    /**
     * ValidatorFactory instance.
     */
    private ValidatorFactory validatorFactory;

    static {
        SINGLE_INSTANCE = new RecipeFactory();
    }

    /**
     * Private constructor to prevent the creation of an object outside.
     */
    private RecipeFactory() {
        validatorFactory = ValidatorFactory.getSingleInstance();
    }

    /**
     * Method to create Recipe instance from Json format string.
     * @param rawRecipe Json format string
     * @return Recipe instance
     * @throws RecipeSyntaxException when instance cannot pass validation
     */
    public Recipe createRecipeFromJson(final String rawRecipe)
            throws RecipeSyntaxException {
        CoderFactory coderFactory = CoderFactory.getSingleInstance();
        RecipeDecoder recipeDecoder = coderFactory.getRecipeDecoder();
        Optional<Recipe> optionalRecipe = recipeDecoder.decodeRecipe(rawRecipe);
        Recipe recipe = optionalRecipe.orElseThrow(() ->
                new RecipeSyntaxException(getContentError()));
        return validateRecipe(recipe, rawRecipe);
    }

    private Recipe validateRecipe(final Recipe recipe, final String rawRecipe)
            throws RecipeSyntaxException {
        RecipeValidator recipeValidator = validatorFactory.getRecipeValid();
        if (recipe.getDishName() == null || recipe.getComposition() == null) {
            String msg = String.format("%s: %s", getContentError(), rawRecipe);
            throw new RecipeSyntaxException(msg);
        }
        if (recipeValidator.validateIsRecipeNameEmpty(recipe.getDishName())
                || recipeValidator.validateIsIngrNameEmpty(recipe.
                getComposition())) {
            String msg = String.format("%s: %s", getNameError(), rawRecipe);
            throw new RecipeSyntaxException(msg);
        }
        if (recipeValidator.validateNegativeIngrNum(recipe.getComposition())) {
            String msg = String.format("%s: %s", getAmountError(), rawRecipe);
            throw new RecipeSyntaxException(msg);
        }
        return recipe;
    }

    /**
     * Method to create Recipe instance with an common way.
     * @param dishName recipe or salad name
     * @param composition names of ingredients and its quantity
     * @return object of the Recipe class
     * @throws RecipeSyntaxException when instance cannot pass validation
     */
    public Recipe createCertainRecipe(final String dishName,
                                      final Map<String, Integer> composition)
            throws RecipeSyntaxException {
        RecipeValidator recipeValidator = validatorFactory.getRecipeValid();
        if (dishName == null || composition == null
                || recipeValidator.validateIsRecipeNameEmpty(dishName)
                || recipeValidator.validateIsIngrNameEmpty(composition)) {
            throw new RecipeSyntaxException(getNameError());
        }
        if (recipeValidator.validateNegativeIngrNum(composition)) {
            throw new RecipeSyntaxException(getAmountError());
        }
        Recipe recipe = new Recipe(dishName, composition);
        return recipe;
    }

    /**
     * Get method.
     * @return single instance RecipeFactory
     */
    public static RecipeFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }
}
