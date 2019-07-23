package by.training.task1.service.action;

import by.training.task1.bean.entity.Recipe;
import by.training.task1.bean.entity.Vegetable;
import by.training.task1.bean.entity.Salad;
import by.training.task1.bean.exception.NoSuchIngredientException;
import by.training.task1.bean.exception.RecipeSyntaxException;
import by.training.task1.service.properties.ApplicationProperties;
import by.training.task1.dao.repository.RecipesHandler;
import by.training.task1.dao.repository.SaladsHandler;
import by.training.task1.dao.repository.VegetablesHandler;
import by.training.task1.service.factory.DAOFactory;
import by.training.task1.service.factory.RecipeFactory;
import by.training.task1.service.factory.SaladFactory;
import by.training.task1.service.factory.VegetableFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class Kitchen implements Subscriber<Recipe> {
    /**
     * Path to the file with vegetables.
     */
    private static String vegetablesFileName;
    /**
     * Path to the file with recipes.
     */
    private static String recipesFileName;
    /**
     * Path to the file with created salads.
     */
    private static String saladsFileName;
    /**
     * Events logger.
     */
    private static Logger logger;
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
    /**
     * Files reader repository.
     */
    private DAOFactory daoFactory;
    /**
     * Subscription at SubmissionPublisher.
     */
    private Subscription subscription;
    /**
     * Changes counter.
     */
    private volatile int countChanges = 0;

    static {
        Properties properties = ApplicationProperties.getProperties();
        recipesFileName = properties.getProperty("recipesFileName",
                "recipes.txt");
        vegetablesFileName = properties.getProperty("vegetablesFileName",
                "vegetables.txt");
        saladsFileName = properties.getProperty("saladsFileName",
                "salads.txt");
        logger = LogManager.getLogger(Kitchen.class);
    }

    /**
     * Constructor which initializes repositories.
     */
    public Kitchen() {
        recipesHandler = RecipesHandler.getInstance();
        saladsHandler = SaladsHandler.getInstance();
        vegetablesHandler = VegetablesHandler.getInstance();
        daoFactory = DAOFactory.getSingleInstance();
    }

    /**
     * Method to create salads according recipes.
     */
    public void produceSalads() {
        deliverRecipesFromFile();
        deliverVegetablesFromFile();
        for (Recipe recipe : recipesHandler.readAll()) {
            try {
                Salad salad = SaladFactory.getSingleInstance().makeSalad(recipe,
                        vegetablesHandler.readAll());
                salad.setSaladID(SaladFactory.incrementAndGetOrderID());
                saladsHandler.add(salad);
            } catch (RecipeSyntaxException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Method to create Recipe instances are read from file.
     */
    private void deliverRecipesFromFile() {
        daoFactory.getFileWriteReader().openFileReader(recipesFileName);
        Optional<String> optionalRecipe;
        do {
            optionalRecipe = daoFactory.getFileWriteReader().readNextLine();
            if (optionalRecipe.isPresent()) {
                putRecipesToRepository(optionalRecipe.get());
            }
        } while (optionalRecipe.isPresent());
        daoFactory.getFileWriteReader().closeFileReader();
    }

    private void putRecipesToRepository(final String rawRec) {
        RecipeFactory recipeFactory = RecipeFactory.getSingleInstance();
        try {
            Recipe recipe = recipeFactory.createRecipeFromJson(rawRec);
            recipesHandler.add(recipe);
        } catch (RecipeSyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void deliverVegetablesFromFile() {
        daoFactory.getFileWriteReader().openFileReader(vegetablesFileName);
        Optional<String> optionalVeg;
        do {
            optionalVeg = daoFactory.getFileWriteReader().readNextLine();
            if (optionalVeg.isPresent()) {
                putVegetableToRepository(optionalVeg.get());
            }
        } while (optionalVeg.isPresent());
        daoFactory.getFileWriteReader().closeFileReader();
    }

    private void putVegetableToRepository(final String rawVeg) {
        VegetableFactory factory = VegetableFactory.getSingleInstance();
        try {
            Vegetable vegetable = factory.createVegFromJson(rawVeg);
            vegetablesHandler.add(vegetable);
        } catch (NoSuchIngredientException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Write the result of created and if necessary sorted salads to the file.
     * @param salads list of salads
     */
    public void writeResultToFile(final List<Salad> salads) {
        daoFactory.getFileWriteReader().openFileWriter(saladsFileName, false);
        for (Salad salad : salads) {
            daoFactory.getFileWriteReader().writeNextLine(salad.toString());
        }
        daoFactory.getFileWriteReader().closeFileWriter();
    }

    /**
     * Subscribe at SubmissionPublisher.
     *
     * @param newSubscription new subscription
     */
    @Override
    public void onSubscribe(final Subscription newSubscription) {
        this.subscription = newSubscription;
        this.subscription.request(1);
    }

    /**
     * Method that listens recipe changes.
     *
     * @param item Recipe instance
     */
    @Override
    public void onNext(final Recipe item) {
        remakeSalad(item);
        subscription.request(1);
        countChanges++;
    }

    /**
     * Method listens exceptions that would be able occurred.
     *
     * @param throwable Throwable instance
     */
    @Override
    public void onError(final Throwable throwable) {
        logger.error(throwable.toString());
    }

    /**
     * Method listens when the event is over.
     */
    @Override
    public void onComplete() {
    }

    private void remakeSalad(final Recipe item) {
        try {
            Salad salad = SaladFactory.getSingleInstance().makeSalad(item,
                    vegetablesHandler.readAll());
            saladsHandler.update(salad);
        } catch (RecipeSyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Get method.
     *
     * @return changes counter
     */
    public int getCountChanges() {
        return countChanges;
    }
}
