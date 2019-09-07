package by.training.taskXML.main;

import by.training.taskXML.bean.Candy;
import by.training.taskXML.controller.CandiesBuilderFactory;
import by.training.taskXML.service.CandiesAbstractBuilder;

public final class Main {
    /**
     * Argument for the create builder method.
     */
    private static final String SAX_PARSER = "SAX";
    /**
     * Argument for the create builder method.
     */
    private static final String DOM_PARSER = "DOM";

    private Main() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        CandiesAbstractBuilder builder = CandiesBuilderFactory.
                getBuilderFactory().createBuilder(
                        SAX_PARSER, CandiesAbstractBuilder.getXsdFileName(),
                CandiesAbstractBuilder.getXmlFileName());
        builder.buildSetCandies();
        for (Candy candy : builder.getCandies()) {
            System.out.println(candy);
        }
    }
}
