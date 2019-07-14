package by.training.task1.coder;

import by.training.task1.bean.entity.Vegetable;
import by.training.task1.bean.entity.BeanVegetable;
import by.training.task1.bean.entity.BulbousVegetable;
import by.training.task1.bean.entity.FruitVegetable;
import by.training.task1.bean.entity.LeafyVegetable;
import by.training.task1.bean.entity.RootVegetable;
import by.training.task1.bean.enumerations.VegetableGroup;
import by.training.task1.bean.exception.NoSuchIngredientException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Class converter from json format string to vegetable object.
 */
public class VegetableDecoder {
    /**
     * Variable which contains object of Gson class.
     */
    private static Gson gson = new Gson();

    /**
     * Method to decode json format string to vegetable object.
     * @param rawVeg json format string
     * @return object of class optional which contains vegetable object
     * @throws NoSuchIngredientException when json format string cannot decode to vegetable class
     */
    public Optional<Vegetable> decodeVegetable(final String rawVeg) throws NoSuchIngredientException {
        Optional<Vegetable> optionalVeg;
        Type type = defineObjectType(rawVeg);
        try {
            Vegetable vegetable = gson.fromJson(rawVeg, type);
            optionalVeg = Optional.ofNullable(vegetable);
        } catch (JsonSyntaxException e) {
            throw new NoSuchIngredientException(NoSuchIngredientException.getContentError(), e);
        }
        return optionalVeg;
    }

    /**
     * Method to define the object type.
     * @param rawVeg
     * @return Type of the specific vegetable class
     */
    private Type defineObjectType(final String rawVeg) {
        if (rawVeg.toLowerCase().contains(VegetableGroup.BEAN.getGroupName())) {
            return BeanVegetable.class;
        } else if (rawVeg.toLowerCase().contains(VegetableGroup.BULBOUS.getGroupName())) {
            return BulbousVegetable.class;
        } else if (rawVeg.toLowerCase().contains(VegetableGroup.FRUIT.getGroupName())) {
            return FruitVegetable.class;
        } else if (rawVeg.toLowerCase().contains(VegetableGroup.LEAF.getGroupName())) {
            return LeafyVegetable.class;
        } else if (rawVeg.toLowerCase().contains(VegetableGroup.ROOT.getGroupName())) {
            return RootVegetable.class;
        } else {
            throw new NoSuchIngredientException(NoSuchIngredientException.getGroupError());
        }
    }
}
