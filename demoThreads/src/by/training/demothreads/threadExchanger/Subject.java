package by.training.demothreads.threadExchanger;

import java.util.concurrent.Exchanger;

public class Subject {
    /**
     * Exchanger object.
     */
    protected static Exchanger<Item> exchanger = new Exchanger<>();
    /**
     * producer or retailer name.
     */
    private String name;
    /**
     * product.
     */
    protected Item item;

    /**
     * Constructor which receive two arguments.
     *
     * @param newName producer or customer name
     * @param newItem goods
     */
    public Subject(final String newName, final Item newItem) {
        this.name = newName;
        this.item = newItem;
    }

    /**
     * Get method.
     *
     * @return producer or retailer name
     */
    public String getName() {
        return name;
    }

    /**
     * Set method.
     *
     * @param newName name
     */
    public void setName(final String newName) {
        name = newName;
    }

    /**
     * Getter.
     *
     * @return goods
     */
    public Item getItem() {
        return item;
    }

    /**
     * Setter.
     *
     * @param newItem item
     */
    public void setItem(final Item newItem) {
        item = newItem;
    }
}
