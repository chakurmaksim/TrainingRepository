package by.training.task1.service.action;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.entity.Vegetable;
import by.training.task1.bean.entity.Salad;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.bean.exception.RecipeSyntaxException;
import by.training.task1.dao.filereader.FileWriteReader;
import by.training.task1.service.properties.ApplicationProperties;
import by.training.task1.dao.repository.RecipesHandler;
import by.training.task1.dao.repository.SaladsHandler;
import by.training.task1.dao.repository.VegetablesHandler;
import by.training.task1.service.factory.DAOFactory;
import by.training.task1.service.factory.RecipeFactory;
import by.training.task1.service.factory.SaladFactory;
import by.training.task1.service.factory.VegetableFactory;

import java.util.Optional;
import java.util.Properties;

public class Kitchen {
    /**
     * Path to the file with vegetables.
     */
    private static String vegetablesFileName;
    /**
     * Path to the file with recipes.
     */
    private static String recipesFileName;
    /**
     * Recipes repository.
     */
    private RecipesHandler recipesHandler;
    /**
     * Salads Repository.
     */
    private SaladsHandler saladsHandler;
    /**
     * Vegetables repository.
     */
    private VegetablesHandler vegetablesHandler;

    static {
        Properties properties = ApplicationProperties.getProperties();
        recipesFileName = properties.getProperty("recipesFileName",
                "recipes.txt");
        vegetablesFileName = properties.getProperty("vegetablesFileName",
                "vegetables.txt");
    }

    /**
     * Constructor which initializes repositories.
     */
    public Kitchen() {
        recipesHandler = RecipesHandler.getInstance();
        saladsHandler = SaladsHandler.getInstance();
        vegetablesHandler = VegetablesHandler.getInstance();
    }

    /**
     * Method to create salads according recipes.
     */
    public void produceSalads() {
        deliverRecipesFromFile();
        deliverVegetablesFromFile();
        SaladFactory saladFactory = SaladFactory.getSingleInstance();
        for (Recipe recipe : recipesHandler.getRecipes()) {
            try {
                Salad salad = saladFactory.makeSalad(recipe,
                        vegetablesHandler.getVegetableSet());
                saladsHandler.add(salad);
            } catch (RecipeSyntaxException e) {
                //  логирование события
            }
        }
    }

    /**
     * Method to create Recipe instances are read from file.
     */
    private void deliverRecipesFromFile() {
        DAOFactory daoFactory = DAOFactory.getSingleInstance();
        FileWriteReader fileReader = daoFactory.getFileWriteReader();
        fileReader.openFileReader(recipesFileName);
        Optional<String> optionalRec;
        do {
            optionalRec = fileReader.readNextLine();
            if (optionalRec.isPresent()) {
                putRecipesToRepository(optionalRec.get());
            }
        } while (optionalRec.isPresent());
        fileReader.closeFileReader();
    }

    private void putRecipesToRepository(final String rawRec) {
        RecipeFactory recipeFactory = RecipeFactory.getSingleInstance();
        try {
            Recipe recipe = recipeFactory.createRecipeFromJson(rawRec);
            recipesHandler.getRecipes().add(recipe);
        } catch (RecipeSyntaxException e) {
            // логирование события
        }
    }

    private void deliverVegetablesFromFile() {
        DAOFactory daoFactory = DAOFactory.getSingleInstance();
        FileWriteReader fileReader = daoFactory.getFileWriteReader();
        fileReader.openFileReader(vegetablesFileName);
        Optional<String> optionalVeg;
        do {
            optionalVeg = fileReader.readNextLine();
            if (optionalVeg.isPresent()) {
                putVegetableToRepository(optionalVeg.get());
            }
        } while (optionalVeg.isPresent());
        fileReader.closeFileReader();
    }

    private void putVegetableToRepository(final String rawVeg) {
        VegetableFactory factory = VegetableFactory.getSingleInstance();
        try {
            Vegetable vegetable = factory.createVegFromJson(rawVeg);
            vegetablesHandler.getVegetableSet().add(vegetable);
        } catch (NoSuchIngredientException e) {
            // логирование события
        }
    }
}
