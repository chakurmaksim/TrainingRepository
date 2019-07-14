package by.training.task1.coder;

import by.training.task1.bean.entity.Vegetable;
import com.google.gson.Gson;

public class VegetableEncoder {
    /**
     * Variable which contains object of Gson class.
     */
    private static Gson gson = new Gson();

    /**
     * Method to encode Recipe object to json format string.
     * @param vegetable Vegetable instance
     * @return json format string
     */
    public String encodeVegetable(final Vegetable vegetable) {
        return gson.toJson(vegetable);
    }
}
