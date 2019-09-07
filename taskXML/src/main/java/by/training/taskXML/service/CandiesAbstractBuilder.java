package by.training.taskXML.service;

import by.training.taskXML.bean.Candy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class CandiesAbstractBuilder {
    /**
     * XML file name.
     */
    private static final String XML_FILE_NAME;
    /**
     * XSD schema file name.
     */
    private static final String XSD_FILE_NAME;
    /**
     * Schema constant name.
     */
    private static final String SCHEMA_CONSTANT;
    /**
     * SchemaFactory instance.
     */
    private static final SchemaFactory XSD_FACTORY;
    /**
     * Logger writes to the log file and to the console.
     */
    private static Logger logger = LogManager.getLogger();
    /**
     * Schema instance.
     */
    private Schema schema;
    /**
     * Candies set.
     */
    private Set<Candy> candies;

    static {
        XML_FILE_NAME = "data/candies.xml";
        XSD_FILE_NAME = "data/candies.xsd";
        SCHEMA_CONSTANT = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        XSD_FACTORY = SchemaFactory.newInstance(SCHEMA_CONSTANT);
    }

    /**
     * Instantiating candies and schema instances.
     * @param xsdFileName XML file name
     */
    public CandiesAbstractBuilder(final String xsdFileName) {
        candies = new HashSet<>();
        try {
            schema = XSD_FACTORY.newSchema(new File(xsdFileName));
        } catch (SAXException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Parse a XML document, create candies and put its into the candies set.
     */
    public abstract void buildSetCandies();

    /**
     * Get method.
     *
     * @return XML file name
     */
    public static String getXmlFileName() {
        return XML_FILE_NAME;
    }

    /**
     * Get method.
     *
     * @return XSD file name
     */
    public static String getXsdFileName() {
        return XSD_FILE_NAME;
    }

    /**
     * Get method.
     *
     * @return logger
     */
    protected static Logger getLogger() {
        return logger;
    }

    /**
     * Get method.
     *
     * @return schema
     */
    protected Schema getSchema() {
        return schema;
    }

    /**
     * Get method.
     *
     * @return candies set
     */
    public Set<Candy> getCandies() {
        return candies;
    }

    /**
     * Set method.
     *
     * @param newCandies candies set
     */
    protected void setCandies(final Set<Candy> newCandies) {
        candies = newCandies;
    }
}
