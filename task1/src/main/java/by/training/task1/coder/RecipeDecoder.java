package by.training.task1.coder;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.exception.RecipeSyntaxException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Optional;

public class RecipeDecoder {
    /**
     * Variable which contains object of Gson class.
     */
    private static Gson gson = new Gson();

    /**
     * Method to decode json format string to Recipe object.
     * @param rawRecipe json format string
     * @return object of class optional which contains Recipe object
     * @throws RecipeSyntaxException when json format string cannot decode
     */
    public Optional<Recipe> decodeRecipe(final String rawRecipe)
            throws RecipeSyntaxException {
        Optional<Recipe> optionalRec;
        try {
            Recipe recipe = gson.fromJson(rawRecipe, Recipe.class);
            optionalRec = Optional.ofNullable(recipe);
        } catch (JsonSyntaxException e) {
            throw new RecipeSyntaxException(RecipeSyntaxException.
                    getContentError(), e);
        }
        return optionalRec;
    }
}
