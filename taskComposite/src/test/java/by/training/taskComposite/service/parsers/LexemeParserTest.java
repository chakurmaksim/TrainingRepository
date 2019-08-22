package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Sentence;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LexemeParserTest {
    LexemeParser parser;
    Sentence sentence;
    String initialText;
    String expected;

    @BeforeMethod
    public void setUp() {
        parser = new LexemeParser();
        sentence = new Sentence();
        initialText = "As-so  we smart, those  money   in.";
        expected = "As-so we smart, those money in.";
    }

    @AfterMethod
    public void tearDown() {
        parser = null;
        sentence = null;
        initialText = null;
        expected = null;
    }

    @Test(description = "Positive scenario of parsing a part of string")
    public void testParse() {
        parser.parse(sentence, initialText);
        String actual = sentence.concatenate();
        assertEquals(actual, expected);
    }
}