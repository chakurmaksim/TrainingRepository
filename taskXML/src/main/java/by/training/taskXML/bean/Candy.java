package by.training.taskXML.bean;

import java.time.LocalDate;

public class Candy<T extends CandyType> {
    /**
     * Candy name.
     */
    private String name;
    /**
     * Candy barcode.
     */
    private long barcode;
    /**
     * Candy type (chocolate, caramel, iris).
     */
    private T candieType;
    /**
     * Composition of the candy.
     */
    private String composition;
    /**
     * Manufacture date of the candy.
     */
    private LocalDate date;
    /**
     * Unit of measurement of the candy expiration date (by default - months).
     */
    private ExpirationMeasure expirationMeasure = ExpirationMeasure.MONTHS;
    /**
     * Candy expiration date.
     */
    private int shelfLife;
    /**
     * Producer of the candy.
     */
    private Producer producer;
    /**
     * Nutritional  value.
     */
    private NutritionValue nutritionValue;

    /**
     * Instantiating of the candy producer and nutritional value instances.
     */
    public Candy() {
        producer = new Producer();
        nutritionValue = new NutritionValue();
    }

    public class NutritionValue {
        /**
         * Amount of proteins per 100 g of the candy.
         */
        private double proteins;
        /**
         * Amount of fats per 100 g of the candy.
         */
        private double fats;
        /**
         * Amount of carbohydrates per 100 g of the candy.
         */
        private double carbohydrates;
        /**
         * Amount of kilocalories per 100 g of the candy.
         */
        private int energy;

        /**
         * Get method.
         *
         * @return amount of proteins per 100 gram of a candy with double format
         */
        public double getProteins() {
            return proteins;
        }

        /**
         * Set method.
         *
         * @param newProteins amount of proteins per 100 gram of a candy
         */
        public void setProteins(final double newProteins) {
            proteins = newProteins;
        }

        /**
         * Get method.
         *
         * @return amount of fats per 100 gram of a candy with double format
         */
        public double getFats() {
            return fats;
        }

        /**
         * Set method.
         *
         * @param newFats amount of fats per 100 gram of a cand
         */
        public void setFats(final double newFats) {
            fats = newFats;
        }

        /**
         * Get method.
         *
         * @return amount of carbohydrates per 100 gram of a candy
         */
        public double getCarbohydrates() {
            return carbohydrates;
        }

        /**
         * Set method.
         *
         * @param newCarbohydrates amount of carbohydrates per 100 gram
         */
        public void setCarbohydrates(final double newCarbohydrates) {
            carbohydrates = newCarbohydrates;
        }

        /**
         * Get method.
         *
         * @return amount of kilocalories per 100 gram of a candy
         */
        public int getEnergy() {
            return energy;
        }

        /**
         * Set method.
         *
         * @param newEnergy amount of kilocalories per 100 gram of a candy
         */
        public void setEnergy(final int newEnergy) {
            energy = newEnergy;
        }

        /**
         * Overridden toString() method.
         *
         * @return string representation of the NutritionValue object
         */
        @Override
        public String toString() {
            return "NutritionValue{"
                    + "proteins=" + proteins + "g"
                    + ", fats=" + fats + "g"
                    + ", carbohydrates=" + carbohydrates + "g"
                    + ", energy=" + energy + "kcal"
                    + '}';
        }
    }

    public enum ExpirationMeasure {
        /**
         * Unit of measurement of the candy expiration date.
         */
        MONTHS("months"),
        /**
         * Unit of measurement of the candy expiration date.
         */
        YEARS("years");

        /**
         * Description.
         */
        private String measure;

        ExpirationMeasure(final String newMeasure) {
            this.measure = newMeasure;
        }

        /**
         * Get method.
         *
         * @return description of the unit of measurement
         */
        public String getMeasure() {
            return measure;
        }
    }

    /**
     * Get method.
     *
     * @return candy name
     */
    public String getName() {
        return name;
    }

    /**
     * Set method.
     *
     * @param newName candy name
     */
    public void setName(final String newName) {
        name = newName;
    }

    /**
     * Get method.
     *
     * @return candy barcode
     */
    public long getBarcode() {
        return barcode;
    }

    /**
     * Set method.
     *
     * @param newBarcode candy barcode
     */
    public void setBarcode(final long newBarcode) {
        barcode = newBarcode;
    }

    /**
     * Get method.
     *
     * @return candy type
     */
    public T getCandieType() {
        return candieType;
    }

    /**
     * Set method.
     *
     * @param newCandieType candy type
     */
    public void setCandieType(final T newCandieType) {
        candieType = newCandieType;
    }

    /**
     * Get method.
     *
     * @return candy composition
     */
    public String getComposition() {
        return composition;
    }

    /**
     * Set method.
     *
     * @param newComposition candy composition
     */
    public void setComposition(final String newComposition) {
        composition = newComposition;
    }

    /**
     * Get method.
     *
     * @return manufacture date of the candy
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set method.
     *
     * @param newDate manufacture date of the candy
     */
    public void setDate(final LocalDate newDate) {
        date = newDate;
    }

    /**
     * Get method.
     *
     * @return unit of measurement of the candy expiration date
     */
    public ExpirationMeasure getExpirationMeasure() {
        return expirationMeasure;
    }

    /**
     * Set method.
     *
     * @param newExpirationMeasure unit of measurement
     */
    public void setExpirationMeasure(
            final ExpirationMeasure newExpirationMeasure) {
        expirationMeasure = newExpirationMeasure;
    }

    /**
     * Get method.
     *
     * @return candy expiration date.
     */
    public int getShelfLife() {
        return shelfLife;
    }

    /**
     * Set method.
     *
     * @param newShelfLife candy expiration date
     */
    public void setShelfLife(final int newShelfLife) {
        shelfLife = newShelfLife;
    }

    /**
     * Get method.
     *
     * @return producer of the candy
     */
    public Producer getProducer() {
        return producer;
    }

    /**
     * set method.
     *
     * @param newProducer producer of the candy
     */
    public void setProducer(final Producer newProducer) {
        producer = newProducer;
    }

    /**
     * Get method.
     *
     * @return nutritional  value
     */
    public NutritionValue getNutritionValue() {
        return nutritionValue;
    }

    /**
     * Set method.
     *
     * @param newNutritionValue nutritional  value
     */
    public void setNutritionValue(final NutritionValue newNutritionValue) {
        nutritionValue = newNutritionValue;
    }

    /**
     * Overridden toString() method.
     *
     * @return string representation of the candy object
     */
    @Override
    public String toString() {
        return "\nCandy{"
                + "name='" + name + '\''
                + ", barcode=" + barcode
                + ", candieType=" + candieType
                + ",\ncomposition='" + composition + '\''
                + ",\ndate=" + date
                + ", shelfLife=" + shelfLife
                + expirationMeasure.getMeasure()
                + ",\nproducer=" + producer
                + ",\nnutritionValue=" + nutritionValue
                + '}';
    }
}
