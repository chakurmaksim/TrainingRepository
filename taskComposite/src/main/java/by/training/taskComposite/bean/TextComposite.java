package by.training.taskComposite.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TextComposite implements TextComponent {
    /**
     * List with the text components.
     */
    private List<TextComponent> textComponents;
    /**
     * Delimiter is used to restore text from text components.
     */
    private String delimiter;

    /**
     * Constructor for instantiating list with components and delimiter.
     *
     * @param newDelimiter delimiter
     */
    public TextComposite(final String newDelimiter) {
        this.delimiter = newDelimiter;
        textComponents = new LinkedList<>();
    }

    /**
     * Add new component to the list.
     *
     * @param comp text component
     */
    public void add(final TextComponent comp) {
        textComponents.add(comp);
    }

    /**
     * Remove component from the list.
     *
     * @param comp text component
     */
    public void remove(final TextComponent comp) {
        textComponents.remove(comp);
    }

    /**
     * Find in the list by index and get text component.
     *
     * @param index number of position
     * @return text component
     */
    public Object getChild(final int index) {
        return textComponents.get(index);
    }

    /**
     * Method to restore text from components.
     *
     * @return strings of the text
     */
    @Override
    public String concatenate() {
        return textComponents.stream().map(TextComponent::concatenate)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * Get method.
     *
     * @return stream of the list that contains text components
     */
    public Stream<TextComponent> getTextComponentStream() {
        return textComponents.stream();
    }

    /**
     * Get method.
     *
     * @return list size
     */
    public int getComponentsListSize() {
        return textComponents.size();
    }
}
