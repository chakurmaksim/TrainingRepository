package by.training.demothreads.theadPhaser;

public class Item {
    /**
     * goods number.
     */
    private int registrationNumber;

    /**
     * Constructor.
     * @param number goods registration number
     */
    public Item(final int number) {
        this.registrationNumber = number;
    }

    /**
     * Get method.
     * @return goods registration number
     */
    public int getRegistrationNumber() {
        return registrationNumber;
    }
}
