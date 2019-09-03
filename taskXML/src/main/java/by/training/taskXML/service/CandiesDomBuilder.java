package by.training.taskXML.service;

import by.training.taskXML.bean.Candy;
import by.training.taskXML.bean.CandyEnum;
import by.training.taskXML.bean.CandyType;
import by.training.taskXML.bean.CandyTypeEnum;
import by.training.taskXML.bean.Producer;
import by.training.taskXML.bean.ProducerEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CandiesDomBuilder extends CandiesAbstractBuilder {
    /**
     * Declaration of the DocumentBuilder variable.
     */
    private DocumentBuilder builder;
    /**
     * EnumSet of candy type enum.
     */
    private EnumSet<CandyTypeEnum> typeEnumSet;
    /**
     * String representation of the candy type enum.
     */
    private List<String> candyTypeList;
    /**
     * EnumSet of the producer address.
     */
    private EnumSet<ProducerEnum> addressEnumSet;
    /**
     * String representation of the producer address.
     */
    private List<String> addressList;

    /**
     * Constructor to instantiate variables.
     */
    public CandiesDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        factory.setSchema(getSchema());
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            getLogger().error(e.toString());
        }
        typeEnumSet = EnumSet.of(CandyTypeEnum.CARAMEL,
                CandyTypeEnum.CHOCOLATE, CandyTypeEnum.IRIS);
        candyTypeList = typeEnumSet.stream().map(e -> e.getValue()).collect(
                Collectors.toList());
        addressEnumSet = EnumSet.range(ProducerEnum.COUNTRY,
                ProducerEnum.CORPS);
        addressList = addressEnumSet.stream().map(e -> e.getValue()).collect(
                Collectors.toList());
    }

    /**
     * Parse XML document.
     */
    @Override
    public void buildSetCandies() {
        Document document;
        try {
            document = builder.parse(getXmlFileName());
            Element root = document.getDocumentElement();
            NodeList candiesList = root.getElementsByTagName(
                    CandyEnum.CANDY.getValue());
            for (int i = 0; i < candiesList.getLength(); i++) {
                Element candyElement = (Element) candiesList.item(i);
                Candy candy = buildCandy(candyElement);
                if (candy != null) {
                    getCandies().add(candy);
                }
            }
        } catch (SAXException | IOException e) {
            getLogger().error(e.toString());
        }
    }

    private Candy buildCandy(final Element candyElement) {
        long candyBarcode = readBarcode(candyElement);
        String candyName = readElementTextContent(candyElement,
                CandyEnum.CANDYNAME.getValue());
        Candy candy = null;
        try {
            candy = createCandy(candyElement);
            candy.setBarcode(candyBarcode);
            candy.setName(candyName);
            candy.setComposition(readElementTextContent(candyElement,
                    CandyEnum.COMPOSITION.getValue()));
            specifyManufacturedate(candy, candyElement);
            specifyShelfLife(candy, candyElement);
            instantiateProducer(candy, candyElement);
            specifyNutritionalValue(candy, candyElement);
        } catch (EnumConstantNotPresentException | NumberFormatException e) {
            getLogger().error(e.toString());
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            getLogger().error(e.toString());
        }
        return candy;
    }

    private String readElementTextContent(
            final Element element, final String elementName) {
        String content = "";
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        if (node != null) {
            content = node.getTextContent();
        }
        return content;
    }

    private long readBarcode(final Element candyElement)
            throws NumberFormatException {
        String code = candyElement.getAttribute(CandyEnum.BARCODE.getValue());
        Pattern barcodePattern = Pattern.compile("[0-9]{13}");
        Matcher barcodeMatcher = barcodePattern.matcher(code);
        long barcode = 0;
        if (barcodeMatcher.find()) {
            barcode = Long.valueOf(barcodeMatcher.group());
        }
        return barcode;
    }

    private Candy createCandy(final Element candyElement)
            throws EnumConstantNotPresentException {
        Candy candy = null;
        NodeList nodeList = candyElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node typeNode = nodeList.item(i);
            if (typeNode != null
                    && candyTypeList.contains(typeNode.getLocalName())) {
                CandyTypeEnum typeEnum = CandyTypeEnum.valueOf(
                        typeNode.getLocalName().toUpperCase());
                Element typeElement = (Element) typeNode;
                switch (typeEnum) {
                    case CHOCOLATE:
                        candy = new Candy<CandyType.Chocolate>();
                        candy.setCandieType(new CandyType.Chocolate());
                        ((CandyType.Chocolate) candy.getCandieType()).
                                setGlaze(readElementTextContent(typeElement,
                                        CandyTypeEnum.GLAZE.getValue()));
                        ((CandyType.Chocolate) candy.getCandieType()).setBody(
                                readElementTextContent(typeElement,
                                        CandyTypeEnum.BODY.getValue()));
                        break;
                    case CARAMEL:
                        candy = new Candy<CandyType.Caramel>();
                        candy.setCandieType(new CandyType.Caramel());
                        ((CandyType.Caramel) candy.getCandieType()).
                                setProcessingMethod(readElementTextContent(
                                        typeElement, CandyTypeEnum.
                                                PROCESSINGMETHOD.getValue()));
                        ((CandyType.Caramel) candy.getCandieType()).setGrade(
                                readElementTextContent(typeElement,
                                        CandyTypeEnum.GRADE.getValue()));
                        break;
                    case IRIS:
                        candy = new Candy<CandyType.Iris>();
                        candy.setCandieType(new CandyType.Iris());
                        ((CandyType.Iris) candy.getCandieType()).
                                setProductionMethod(readElementTextContent(
                                        typeElement, CandyTypeEnum.
                                                PRODUCTIONMETHOD.getValue()));
                        ((CandyType.Iris) candy.getCandieType()).setStructure(
                                readElementTextContent(typeElement,
                                        CandyTypeEnum.STRUCTURE.getValue()));
                        break;
                    default:
                        throw new EnumConstantNotPresentException(
                                typeEnum.getDeclaringClass(),
                                typeEnum.getValue());
                }
                assignCandyTypeAttr(candy, typeNode);
            }
        }
        return candy;
    }

    private void assignCandyTypeAttr(final Candy candy, final Node typeNode) {
        if (typeNode.hasAttributes()) {
            NamedNodeMap nodeMap = typeNode.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node attr = nodeMap.item(i);
                if (CandyTypeEnum.ISWRAPPED.getValue().equals(
                        attr.getLocalName())) {
                    candy.getCandieType().setWrapped(
                            Boolean.valueOf(attr.getNodeValue()));
                } else if (CandyTypeEnum.ISGLAZED.getValue().equals(
                        attr.getLocalName())) {
                    candy.getCandieType().setGlazed(
                            Boolean.valueOf(attr.getNodeValue()));
                }
            }
        }
    }

    private boolean specifyManufacturedate(
            final Candy candy, final Element candyElement)
            throws ArrayIndexOutOfBoundsException, NumberFormatException {
        String content = readElementTextContent(candyElement,
                CandyEnum.MANUFACTUREDATE.getValue());
        String[] dateArr = content.split("-");
        int year = Integer.valueOf(dateArr[0]);
        int month = Integer.valueOf(dateArr[1]);
        int day = Integer.valueOf(dateArr[2]);
        LocalDate date = LocalDate.of(year, month, day);
        candy.setDate(date);
        return true;
    }

    private boolean specifyShelfLife(
            final Candy candy, final Element candyElement)
            throws NumberFormatException, IllegalArgumentException {
        NodeList nodeList = candyElement.getElementsByTagName(
                CandyEnum.SHELFLIFE.getValue());
        Node shelfLifeNode = nodeList.item(0);
        Element shelfLifeElement = (Element) shelfLifeNode;
        String m = shelfLifeElement.getAttribute(CandyEnum.MEASURE.getValue());
        String content = readElementTextContent(candyElement,
                CandyEnum.SHELFLIFE.getValue());
        candy.setExpirationMeasure(
                Candy.ExpirationMeasure.valueOf(m.toUpperCase()));
        candy.setShelfLife(Integer.valueOf(content));
        return true;
    }

    private Producer instantiateProducer(
            final Candy candy, final Element candyElement)
            throws NumberFormatException, EnumConstantNotPresentException {
        Producer producer = candy.getProducer();
        NodeList nodeList = candyElement.getElementsByTagName(
                ProducerEnum.PRODUCER.getValue());
        Node producerNode = nodeList.item(0);
        Element producerElement = (Element) producerNode;
        String producerName = readElementTextContent(producerElement,
                ProducerEnum.PRODUCERNAME.getValue());
        producer.setName(producerName);
        instantiateAddress(producer, producerElement);
        return producer;
    }

    private Producer.Address instantiateAddress(
            final Producer producer, final Element producerElement)
            throws NumberFormatException, EnumConstantNotPresentException {
        Producer.Address address = producer.getAddress();
        NodeList nodeList = producerElement.getElementsByTagName(
                ProducerEnum.ADDRESS.getValue());
        Node addressNode = nodeList.item(0);
        if (addressNode.hasChildNodes()) {
            NodeList childList = addressNode.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node childNode = childList.item(i);
                if (childNode != null
                        && addressList.contains(childNode.getLocalName())) {
                    ProducerEnum current = ProducerEnum.valueOf(
                            childNode.getLocalName().toUpperCase());
                    switch (current) {
                        case COUNTRY:
                            address.setCountry(readElementTextContent(
                                    producerElement,
                                    ProducerEnum.COUNTRY.getValue()));
                            break;
                        case POSTCODE:
                            try {
                                int postcode = Integer.valueOf(
                                        readElementTextContent(producerElement,
                                        ProducerEnum.POSTCODE.getValue()));
                                address.setPostcode(postcode);
                            } catch (NumberFormatException e) {
                                getLogger().error(e.toString());
                            }
                            break;
                        case REGION:
                            address.setRegion(readElementTextContent(
                                    producerElement,
                                    ProducerEnum.REGION.getValue()));
                            break;
                        case DISTRICT:
                            address.setDistrict(readElementTextContent(
                                    producerElement,
                                    ProducerEnum.DISTRICT.getValue()));
                            break;
                        case CITY:
                            address.setLocality(readElementTextContent(
                                    producerElement,
                                    ProducerEnum.CITY.getValue()));
                            break;
                        case VILLAGE:
                            address.setLocality(readElementTextContent(
                                    producerElement,
                                    ProducerEnum.VILLAGE.getValue()));
                            break;
                        case STREET:
                            address.setStreet(readElementTextContent(
                                    producerElement,
                                    ProducerEnum.STREET.getValue()));
                            break;
                        case BUILDING:
                            try {
                                int building = Integer.valueOf(
                                        readElementTextContent(producerElement,
                                                ProducerEnum.BUILDING.
                                                        getValue()));
                                address.setBuilding(building);
                            } catch (NumberFormatException e) {
                                getLogger().error(e.toString());
                            }
                            break;
                        case CORPS:
                            address.setCorps(readElementTextContent(
                                    producerElement, ProducerEnum.STREET.
                                            getValue()).toCharArray()[0]);
                            break;
                        default:
                            throw new EnumConstantNotPresentException(
                                    current.getDeclaringClass(),
                                    current.getValue());
                    }
                }
            }
        }
        return address;
    }

    private Candy.NutritionValue specifyNutritionalValue(
            final Candy candy, final Element candyElement)
            throws NumberFormatException {
        Candy.NutritionValue nutritionValue = candy.getNutritionValue();
        NodeList nodeList = candyElement.getElementsByTagName(
                CandyEnum.NUTRITIONALVALUE.getValue());
        Node nutritionNode = nodeList.item(0);
        Element nutritionElement = (Element) nutritionNode;
        nutritionValue.setProteins(Double.valueOf(readElementTextContent(
                nutritionElement, CandyEnum.PROTEINS.getValue())));
        nutritionValue.setFats(Double.valueOf(readElementTextContent(
                nutritionElement, CandyEnum.FATS.getValue())));
        nutritionValue.setCarbohydrates(Double.valueOf(readElementTextContent(
                nutritionElement, CandyEnum.CARBOHYDRATES.getValue())));
        nutritionValue.setEnergy(Integer.valueOf(readElementTextContent(
                nutritionElement, CandyEnum.ENERGY.getValue())));
        return nutritionValue;
    }
}
