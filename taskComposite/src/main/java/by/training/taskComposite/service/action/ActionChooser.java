package by.training.taskComposite.service.action;

import by.training.taskComposite.bean.Languages;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActionChooser {
    /**
     * Final variable with the path to the properties file.
     */
    private static final String RESOURCE_FILE_NAME;
    /**
     * Declaration of the Locale variable.
     */
    private Locale locale;
    /**
     * Declaration of the ResourceBundle variable.
     */
    private ResourceBundle bundle;

    static {
        RESOURCE_FILE_NAME = "text";
    }

    /**
     * Instantiating variables Locale and ResourceBundle.
     */
    public ActionChooser() {
        locale = new Locale(Languages.ENGLISH_EN.getLanguage(),
                Languages.ENGLISH_EN.getCountry());
        bundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale);
    }

    /**
     * Method reads from resource file and offer different actions.
     *
     * @return string of available variants of actions.
     */
    public String offerStartAction() {
        String action = String.format("1 - %s; \n2 - %s; \n3 - %s.",
                bundle.getString("language.choose"),
                bundle.getString("action.textFromFile"),
                bundle.getString("exit"));
        return action;
    }

    /**
     * Method reads from resource file and offer different actions of sort text.
     *
     * @return string of available variants of sorts.
     */
    public String offerSortAction() {
        String action = String.format(
                "1 - %s; \n2 - %s; \n3 - %s; \n4 - %s; \n5 - %s.",
                bundle.getString("action.sortParagraphsBySentencesAmount"),
                bundle.getString("action.sortWordsByLength"),
                bundle.getString("action.sortSentencesByWordsAmount"),
                bundle.getString("action.sortLexemesBySymbols"),
                bundle.getString("exit"));
        return action;
    }

    /**
     * Method reads from resource file and offer different languages.
     *
     * @return string of available variants of languages.
     */
    public String offerLanguages() {
        String variant = String.format("1 - %s; 2 - %s; 3 - %s: ",
                bundle.getString("language.english"),
                bundle.getString("language.deutsch"),
                bundle.getString("language.russian"));
        return variant;
    }

    /**
     * Method changes the language of menu bar according to user input.
     *
     * @param userInput number
     */
    public void chooseLanguage(final int userInput) {
        HashMap<Integer, Languages> mapLanguages = new HashMap<>();
        int mapCunter = 1;
        mapLanguages.put(mapCunter, Languages.ENGLISH_EN);
        mapLanguages.put(++mapCunter, Languages.DEUTSCH_DE);
        mapLanguages.put(++mapCunter, Languages.RUSSIAN_RU);
        Languages languages = mapLanguages.get(userInput);
        if (languages != null) {
            locale = new Locale(languages.getLanguage(),
                    languages.getCountry());
            bundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale);
        }
    }
}
