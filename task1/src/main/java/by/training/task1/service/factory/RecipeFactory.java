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

    static {
        SINGLE_INSTANCE = new RecipeFactory();
    }

    /**
     * Private constructor to prevent the creation of an object outside.
     */
    private RecipeFactory() {
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
        Optional<Recipe> optionalRec = recipeDecoder.decodeRecipe(rawRecipe);
        Recipe recipe = optionalRec.orElseThrow(() ->
                new RecipeSyntaxException(getContentError()));
        return validateRec(recipe, rawRecipe);
    }

    private Recipe validateRec(final Recipe recipe, final String rawRecipe)
            throws RecipeSyntaxException {
        ValidatorFactory validatorFactory = ValidatorFactory.
                getSingleInstance();
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
        if (recipeValidator.validateNegIngrNum(recipe.getComposition())) {
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
        ValidatorFactory validatorFact = ValidatorFactory.getSingleInstance();
        RecipeValidator recipeValidator = validatorFact.getRecipeValid();
        if (dishName == null
                || recipeValidator.validateIsRecipeNameEmpty(dishName)
                || recipeValidator.validateIsIngrNameEmpty(composition)) {
            throw new RecipeSyntaxException(getNameError());
        }
        if (recipeValidator.validateNegIngrNum(composition)) {
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
