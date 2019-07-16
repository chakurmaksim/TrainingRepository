package by.training.task1.service.coder;

import by.training.task1.bean.entity.Recipe;
import com.google.gson.Gson;

public class RecipeEncoder {
    /**
     * Variable which contains object of Gson class.
     */
    private static Gson gson = new Gson();

    /**
     * Method to encode Recipe object to json format string.
     * @param recipe Recipe instance
     * @return json format string
     */
    public String encodeRecipe(final Recipe recipe) {
        return gson.toJson(recipe);
    }
}
