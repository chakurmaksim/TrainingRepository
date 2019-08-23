package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Lexeme;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WordParserTest {
    WordParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new WordParser();
    }

    @AfterMethod
    public void tearDown() {
        parser = null;
    }

    @DataProvider(name = "dataForTestParseLexeme")
    public Object[][] createDataFor_TestParseLexeme() {
        String[] arrayStr = new String[]{"Developer,", "Test!"};
        return new Object[][]{
                {arrayStr[0], "Developer,"},
                {arrayStr[1], "Test!"}
        };
    }

    @DataProvider(name = "dataForTestParseWord")
    public Object[][] createDataFor_TestParseWord() {
        String[] arrayStr = new String[]{"'Spider',", "Congratulations!", "Hello."};
        return new Object[][]{
                {arrayStr[0], "'Spider'"},
                {arrayStr[1], "Congratulations"},
                {arrayStr[2], "Hello"}
        };
    }

    @DataProvider(name = "dataForTestParsePunctuation")
    public Object[][] createDataFor_TestParsePunctuation() {
        String[] arrayStr = new String[]{"'Spring',", "Why?", "Cool!"};
        return new Object[][]{
                {arrayStr[0], ","},
                {arrayStr[1], "?"},
                {arrayStr[2], "!"}
        };
    }

    @Test(description = "Positive scenario of parsing string of lexeme",
            dataProvider = "dataForTestParseLexeme")
    public void testParseLexeme(final String initial, final String expected) {
        Lexeme lexeme = new Lexeme();
        parser.parse(lexeme, initial);
        String actual = lexeme.concatenate();
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario of parsing string to word",
            dataProvider = "dataForTestParseWord")
    public void testParseWord(final String initial, final String expected) {
        Lexeme lexeme = new Lexeme();
        parser.parse(lexeme, initial);
        String actual = lexeme.getChild(0).concatenate();
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario of parsing string to punctuation",
            dataProvider = "dataForTestParsePunctuation")
    public void testParsePunctuation(final String initial, final String expected) {
        Lexeme lexeme = new Lexeme();
        parser.parse(lexeme, initial);
        String actual = lexeme.getChild(1).concatenate();
        assertEquals(actual, expected);
    }
}