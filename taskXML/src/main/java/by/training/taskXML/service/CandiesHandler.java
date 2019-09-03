package by.training.taskXML.service;

import by.training.taskXML.bean.Candy;
import by.training.taskXML.bean.CandyEnum;
import by.training.taskXML.bean.CandyType;
import by.training.taskXML.bean.CandyTypeEnum;
import by.training.taskXML.bean.ProducerEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CandiesHandler extends DefaultHandler {
    /**
     * Logger prints to the console only.
     */
    private static Logger loggerInfo = LogManager.getRootLogger();
    /**
     * Logger prints to the log file and to the console.
     */
    private static Logger loggerError = LogManager.getLogger();
    /**
     * Candies set.
     */
    private Set<Candy> candies;
    /**
     * Candy instance.
     */
    private Candy candy;
    /**
     * Candy enum instance.
     */
    private CandyEnum candyEnum = null;
    /**
     * Producer enum instance.
     */
    private ProducerEnum producerEnum = null;
    /**
     * Candy type enum instance.
     */
    private CandyTypeEnum typeEnum = null;
    /**
     * EnumSet of candy enum.
     */
    private EnumSet<CandyEnum> candyEnumSet;
    /**
     * EnumSet of candy type enum.
     */
    private EnumSet<CandyTypeEnum> typeEnumSet;
    /**
     * EnumSet of candy type enum.
     */
    private EnumSet<CandyTypeEnum> typeElementEnumSet;
    /**
     * EnumSet of producer enum.
     */
    private EnumSet<ProducerEnum> producerEnumSet;
    /**
     * String representation of the candy enum.
     */
    private List<String> candyList;
    /**
     * String representation of the candy type enum.
     */
    private List<String> candyTypeList;
    /**
     * String representation of the producer enum.
     */
    private List<String> producerList;

    /**
     * Instantiating variables.
     */
    public CandiesHandler() {
        candies = new HashSet<>();
        candyEnumSet = EnumSet.range(CandyEnum.CANDYNAME, CandyEnum.ENERGY);
        typeEnumSet = EnumSet.of(CandyTypeEnum.CARAMEL,
                CandyTypeEnum.CHOCOLATE, CandyTypeEnum.IRIS);
        typeElementEnumSet = EnumSet.range(CandyTypeEnum.GLAZE,
                CandyTypeEnum.STRUCTURE);
        producerEnumSet = EnumSet.complementOf(EnumSet.of(
                ProducerEnum.PRODUCER, ProducerEnum.ADDRESS));
        candyList = Arrays.stream(CandyEnum.values()).map(
                e -> e.toString()).collect(Collectors.toList());
        candyTypeList = Arrays.stream(CandyTypeEnum.values()).map(
                e -> e.toString()).collect(Collectors.toList());
        producerList = Arrays.stream(ProducerEnum.values()).map(
                e -> e.toString()).collect(Collectors.toList());
    }

    /**
     * The method is called when the document begins to be processed.
     */
    @Override
    public void startDocument() {
        loggerInfo.info("Parsing started.");
    }

    /**
     * Method is called when the analyzer fully processes the
     * contents of the opening tag.
     *
     * @param nameSpace unique namespace name
     * @param localName element name without prefix
     * @param qName     prefixed element name
     * @param attrs     list of attributes
     */
    @Override
    public void startElement(
            final String nameSpace, final String localName,
            final String qName, final Attributes attrs) {
        if (CandyEnum.CANDY.getValue().equals(localName)) {
            candy = new Candy();
            if (CandyEnum.BARCODE.getValue().equals(
                    attrs.getLocalName(0))) {
                Pattern barcodePattern = Pattern.compile("[0-9]{13}");
                Matcher barcodeMatcher = barcodePattern.matcher(
                        attrs.getValue(0));
                if (barcodeMatcher.find()) {
                    try {
                        long barcode = Long.valueOf(barcodeMatcher.group());
                        candy.setBarcode(barcode);
                    } catch (NumberFormatException e) {
                        loggerError.info(e.toString());
                    }
                }
            }
        } else {
            if (candy != null
                    && candyTypeList.contains(localName.toUpperCase())) {
                candyEnum = null;
                CandyTypeEnum temp = CandyTypeEnum.valueOf(
                        localName.toUpperCase());
                if (typeEnumSet.contains(temp)) {
                    specifyCandyType(localName);
                    assignCandyTypeAttributes(attrs);
                } else if (typeElementEnumSet.contains(temp)) {
                    typeEnum = temp;
                }
            } else if (candy != null
                    && candyList.contains(localName.toUpperCase())) {
                CandyEnum temp = CandyEnum.valueOf(localName.toUpperCase());
                if (candyEnumSet.contains(temp)) {
                    candyEnum = temp;
                    if (CandyEnum.SHELFLIFE.getValue().equals(localName)) {
                        String m = attrs.getValue(CandyEnum.MEASURE.getValue());
                        candy.setExpirationMeasure(Candy.ExpirationMeasure.
                                valueOf(m.toUpperCase()));
                    }
                }
            } else if (candy != null
                    && producerList.contains(localName.toUpperCase())) {
                candyEnum = null;
                ProducerEnum temp = ProducerEnum.valueOf(
                        localName.toUpperCase());
                if (producerEnumSet.contains(temp)) {
                    producerEnum = temp;
                }
            }
        }
    }

    /**
     * Method signals completion of an element.
     *
     * @param nameSpace unique namespace name
     * @param localName element name without prefix
     * @param qName     prefixed element name
     */
    @Override
    public void endElement(
            final String nameSpace, final String localName,
            final String qName) {
        if (CandyEnum.CANDY.getValue().equals(localName)) {
            candies.add(candy);
        }
    }

    /**
     * Method is called if the analyzer has encountered symbolic information
     * inside an element.
     *
     * @param ch    char array
     * @param start first index
     * @param end   length of the string between start and end tags
     */
    @Override
    public void characters(final char[] ch, final int start, final int end) {
        String elemValue = new String(ch, start, end).trim();
        if (candyEnum != null && candy != null) {
            try {
                assignCandyElements(elemValue);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                loggerError.error(e.toString());
            } catch (EnumConstantNotPresentException e) {
                loggerError.error(e.toString());
            }
        } else if (producerEnum != null && candy != null) {
            try {
                assignProducerElements(elemValue);
            } catch (NumberFormatException
                    | EnumConstantNotPresentException e) {
                loggerError.error(e.toString());
            }
        } else if (typeEnum != null && candy != null) {
            try {
                assignCandyTypeElements(elemValue);
            } catch (EnumConstantNotPresentException e) {
                loggerError.error(e.toString());
            }
        }
    }

    /**
     * The method is called when the document finishes to be processed.
     */
    @Override
    public void endDocument() {
        loggerInfo.info("Parsing finished.");
    }

    private void specifyCandyType(final String localName) {
        String candyName = candy.getName();
        long candyCode = candy.getBarcode();
        if (CandyTypeEnum.CHOCOLATE.getValue().equals(localName)) {
            candy = new Candy<CandyType.Chocolate>();
            candy.setCandieType(new CandyType.Chocolate());
        } else if (CandyTypeEnum.CARAMEL.getValue().equals(localName)) {
            candy = new Candy<CandyType.Caramel>();
            candy.setCandieType(new CandyType.Caramel());
        } else if (CandyTypeEnum.IRIS.getValue().equals(localName)) {
            candy = new Candy<CandyType.Iris>();
            candy.setCandieType(new CandyType.Iris());
        }
        candy.setName(candyName);
        candy.setBarcode(candyCode);
    }

    private void assignCandyTypeAttributes(final Attributes attrs) {
        for (int i = 0; i < attrs.getLength(); i++) {
            if (CandyTypeEnum.ISWRAPPED.getValue().equals(
                    attrs.getLocalName(i))) {
                candy.getCandieType().setWrapped(
                        Boolean.valueOf(attrs.getValue(i)));
            } else if (CandyTypeEnum.ISGLAZED.getValue().equals(
                    attrs.getLocalName(i))) {
                candy.getCandieType().setGlazed(
                        Boolean.valueOf(attrs.getValue(i)));
            }
        }
    }

    private boolean assignCandyElements(final String elemValue)
            throws NumberFormatException, ArrayIndexOutOfBoundsException,
            EnumConstantNotPresentException {
        switch (candyEnum) {
            case CANDYNAME:
                candy.setName(elemValue);
                break;
            case COMPOSITION:
                String currentComposition = candy.getComposition();
                if (currentComposition != null) {
                    String result = currentComposition + " " + elemValue;
                    candy.setComposition(result.trim());
                } else {
                    candy.setComposition(elemValue);
                }
                break;
            case MANUFACTUREDATE:
                String[] dateArr = elemValue.split("-");
                int year = Integer.valueOf(dateArr[0]);
                int month = Integer.valueOf(dateArr[1]);
                int day = Integer.valueOf(dateArr[2]);
                LocalDate date = LocalDate.of(year, month, day);
                candy.setDate(date);
                break;
            case SHELFLIFE:
                candy.setShelfLife(Integer.valueOf(elemValue));
                break;
            case PROTEINS:
                candy.getNutritionValue().setProteins(
                        Double.valueOf(elemValue));
                break;
            case FATS:
                candy.getNutritionValue().setFats(Double.valueOf(elemValue));
                break;
            case CARBOHYDRATES:
                candy.getNutritionValue().setCarbohydrates(
                        Double.valueOf(elemValue));
                break;
            case ENERGY:
                candy.getNutritionValue().setEnergy(Integer.valueOf(elemValue));
                break;
            default:
                throw new EnumConstantNotPresentException(
                        candyEnum.getDeclaringClass(), candyEnum.name());
        }
        return true;
    }

    private boolean assignProducerElements(final String elemValue)
            throws NumberFormatException {
        switch (producerEnum) {
            case PRODUCERNAME:
                candy.getProducer().setName(elemValue);
                break;
            case COUNTRY:
                candy.getProducer().getAddress().setCountry(elemValue);
                break;
            case POSTCODE:
                candy.getProducer().getAddress().setPostcode(
                        Integer.valueOf(elemValue));
                break;
            case REGION:
                candy.getProducer().getAddress().setRegion(elemValue);
                break;
            case DISTRICT:
                candy.getProducer().getAddress().setDistrict(elemValue);
                break;
            case CITY:
                candy.getProducer().getAddress().setLocality(elemValue);
                break;
            case VILLAGE:
                candy.getProducer().getAddress().setLocality(elemValue);
            case STREET:
                candy.getProducer().getAddress().setStreet(elemValue);
                break;
            case BUILDING:
                candy.getProducer().getAddress().setBuilding(
                        Integer.valueOf(elemValue));
                break;
            case CORPS:
                candy.getProducer().getAddress().setCorps(
                        elemValue.toCharArray()[0]);
                break;
            default:
                throw new EnumConstantNotPresentException(
                        producerEnum.getDeclaringClass(), producerEnum.name());

        }
        producerEnum = null;
        return true;
    }

    private boolean assignCandyTypeElements(final String elemValue)
            throws EnumConstantNotPresentException {
        switch (typeEnum) {
            case GLAZE:
                if (candy.getCandieType() instanceof CandyType.Chocolate) {
                    ((CandyType.Chocolate) candy.getCandieType()).setGlaze(
                            elemValue);
                }
                break;
            case BODY:
                if (candy.getCandieType() instanceof CandyType.Chocolate) {
                    ((CandyType.Chocolate) candy.getCandieType()).setBody(
                            elemValue);
                }
                break;
            case GRADE:
                if (candy.getCandieType() instanceof CandyType.Caramel) {
                    ((CandyType.Caramel) candy.getCandieType()).setGrade(
                            elemValue);
                }
            case PROCESSINGMETHOD:
                if (candy.getCandieType() instanceof CandyType.Caramel) {
                    ((CandyType.Caramel) candy.getCandieType()).
                            setProcessingMethod(elemValue);
                }
                break;
            case PRODUCTIONMETHOD:
                if (candy.getCandieType() instanceof CandyType.Iris) {
                    ((CandyType.Iris) candy.getCandieType()).
                            setProductionMethod(elemValue);
                }
            case STRUCTURE:
                if (candy.getCandieType() instanceof CandyType.Iris) {
                    ((CandyType.Iris) candy.getCandieType()).
                            setStructure(elemValue);
                }
                break;
            default:
                throw new EnumConstantNotPresentException(
                        typeEnum.getDeclaringClass(), typeEnum.name());
        }
        typeEnum = null;
        return true;
    }

    /**
     * Get method.
     *
     * @return candies set
     */
    public Set<Candy> getCandies() {
        return candies;
    }
}
