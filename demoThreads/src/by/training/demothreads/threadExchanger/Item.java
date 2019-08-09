package by.training.demothreads.threadExchanger;

public class Item {
    /**
     * goods number id.
     */
    private Integer id;
    /**
     * quantity of goods.
     */
    private Integer number;

    /**
     * Constructor with arguments.
     *
     * @param newId     goods number id
     * @param newNumber quantity of goods
     */
    public Item(final Integer newId, final Integer newNumber) {
        super();
        this.id = newId;
        this.number = newNumber;
    }

    /**
     * Getter.
     *
     * @return id number
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter.
     * @return amount of the goods
     */
    public Integer getNumber() {
        return number;
    }
}
