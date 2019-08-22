package by.training.taskComposite.dao;

import by.training.task1.service.specification.SortSpecification;
import by.training.task1.service.specification.Specification;
import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Text;

import java.util.LinkedList;
import java.util.List;

public enum TextHandler implements TextRepository<Text> {
    /**
     * single instance of the MatrixHolder.
     */
    SINGLE_INSTANCE;

    /**
     * Text item.
     */
    private Text text;

    @Override
    public String read() {
        return text != null ? text.concatenate() : "";
    }

    @Override
    public void add(final Text item) {
        text = item;
    }

    @Override
    public void remove(final Text item) {
        if (text != null && text.equals(item)) {
            text = null;
        }
    }

    @Override
    public void update(final Text item) {
    }

    @Override
    public String query(final Specification specification) {
        if (text != null && specification instanceof SortSpecification) {
            List<Paragraph> components = new LinkedList<>();
            text.getTextComponentStream().forEach(
                    x -> components.add((Paragraph) x));
            components.stream().forEach(p -> text.remove(p));
            ((SortSpecification) specification).
                    sortSpecifiedComparator(components);
            components.stream().forEach(p -> text.add(p));
        }
        return text != null ? text.concatenate() : "";
    }
}
