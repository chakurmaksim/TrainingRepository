package by.training.taskXML.main;

import by.training.taskXML.bean.Candy;
import by.training.taskXML.controller.CandiesBuilderFactory;
import by.training.taskXML.service.CandiesAbstractBuilder;

public final class Main {
    private Main() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        CandiesAbstractBuilder builder = CandiesBuilderFactory.
                getBuilderFactory().createBuilder("sax");
        builder.buildSetCandies();
        for (Candy candy : builder.getCandies()) {
            System.out.println(candy);
        }
    }
}
