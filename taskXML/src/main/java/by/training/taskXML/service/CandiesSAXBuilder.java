package by.training.taskXML.service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class CandiesSAXBuilder extends CandiesAbstractBuilder {
    /**
     * SAXParser instance.
     */
    private SAXParser parser;
    /**
     * Default handler instance.
     */
    private CandiesHandler handler;
    /**
     * XML file name.
     */
    private String xmlFileName;

    /**
     * Instantiating variables.
     *
     * @param xsdFileName full path to xsd file
     * @param newXmlFileName full path to xml file
     */
    public CandiesSAXBuilder(
            final String xsdFileName, final String newXmlFileName) {
        super(xsdFileName);
        this.xmlFileName = newXmlFileName;
        handler = new CandiesHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        factory.setSchema(getSchema());
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            getLogger().error(e.toString());
        }
    }

    /**
     * Parse XML document.
     */
    public void buildSetCandies() {
        try {
            parser.parse(xmlFileName, handler);
        } catch (SAXException | IOException e) {
            getLogger().error(e.toString());
        } catch (EnumConstantNotPresentException e) {
            getLogger().error(e.toString());
        }
        setCandies(handler.getCandies());
    }
}
