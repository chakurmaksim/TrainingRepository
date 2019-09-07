package by.training.taskXML.controller;

import by.training.taskXML.service.CandiesAbstractBuilder;
import by.training.taskXML.service.CandiesDomBuilder;
import by.training.taskXML.service.CandiesSAXBuilder;

public final class CandiesBuilderFactory {
    /**
     * Single instance of the CandiesBuilderFactory.
     */
    private static CandiesBuilderFactory builderFactory;

    static {
        builderFactory = new CandiesBuilderFactory();
    }

    private CandiesBuilderFactory() {
    }

    private enum TypeParser {
        /**
         * SAXBuilder.
         */
        SAX,
        /**
         * StAXBuilder.
         */
        STAX,
        /**
         * DOMBuilder.
         */
        DOM
    }

    /**
     * Factory method to create the certain candies builder.
     *
     * @param typeParser string of the type parser
     * @param xsdFileName full path to the xsd file
     * @param xmlFileName full path to the xml file
     * @return candies builder
     */
    public CandiesAbstractBuilder createBuilder(
            final String typeParser, final String xsdFileName,
            final String xmlFileName) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new CandiesDomBuilder(xsdFileName, xmlFileName);
            case SAX:
                return new CandiesSAXBuilder(xsdFileName, xmlFileName);
            default:
                throw new EnumConstantNotPresentException(
                        type.getDeclaringClass(), type.name());
        }
    }

    /**
     * Get method.
     *
     * @return single instance
     */
    public static CandiesBuilderFactory getBuilderFactory() {
        return builderFactory;
    }
}
