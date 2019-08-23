package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Punctuation;
import by.training.taskComposite.bean.Word;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertEquals;

public class SymbolParserTest {
    SymbolParser parser;

    @BeforeTest
    public void setUp() {
        parser = new SymbolParser();
    }

    @AfterTest
    public void tearDown() {
        parser = null;
    }

    @DataProvider(name = "dataForTestParseWord")
    public Object[][] createDataFor_TestParseWord() {
        String[] arrayStr = new String[]{"Present", "Line\n"};
        return new Object[][]{
                {arrayStr[0], "Present"},
                {arrayStr[1], "Line"}
        };
    }

    @DataProvider(name = "dataForTestParsePunctuation")
    public Object[][] createDataFor_TestParsePunctuation() {
        String[] arrayStr = new String[]{".", ",\n", "?!", "..."};
        return new Object[][]{
                {arrayStr[0], "."},
                {arrayStr[1], ","},
                {arrayStr[2], "?!"},
                {arrayStr[3], "..."}
        };
    }

    @Test(description = "Positive scenario of parsing string of word",
            dataProvider = "dataForTestParseWord")
    public void testParseWord(final String initialStr, final String expected) {
        Word word = new Word();
        parser.parse(word, initialStr);
        String actual = word.concatenate();
        assertEquals(actual, expected);
    }

    @Test(description = "Positive scenario of parsing string of punctuation",
            dataProvider = "dataForTestParsePunctuation")
    public void testParsePunctuation(
            final String initialStr, final String expected) {
        Punctuation punctuation = new Punctuation();
        parser.parse(punctuation, initialStr);
        String actual = punctuation.concatenate();
        assertEquals(actual, expected);
    }
}