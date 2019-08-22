package by.training.taskComposite.bean;

public enum Languages {
    /**
     * English language.
     */
    ENGLISH_EN("en", "EN"),
    /**
     * Deutsch language.
     */
    DEUTSCH_DE("de", "DE"),
    /**
     * Russian language.
     */
    RUSSIAN_RU("ru", "RU");

    /**
     * Language abbreviation.
     */
    private String language;
    /**
     * Language affiliation to a country.
     */
    private String country;

    Languages(final String newLanguage, final String newCountry) {
        this.language = newLanguage;
        this.country = newCountry;
    }

    /**
     * Getter.
     *
     * @return language abbreviation
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Getter.
     *
     * @return country abbreviation
     */
    public String getCountry() {
        return country;
    }
}
