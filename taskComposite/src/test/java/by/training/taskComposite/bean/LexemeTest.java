package by.training.taskComposite.bean;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class LexemeTest {
    Lexeme lexeme;
    String expected;

    @BeforeMethod
    public void setUp() {
        lexeme = new Lexeme();
        Symbol[] symbols = new Symbol[]{
                new Symbol('T'), new Symbol('e'),
                new Symbol('s'), new Symbol('t')
        };
        Word word = new Word();
        Arrays.stream(symbols).forEach(s -> word.add(s));
        Punctuation punctuation = new Punctuation();
        punctuation.add(new Symbol('!'));
        lexeme.add(word);
        lexeme.add(punctuation);
        expected = "Test!";
    }

    @AfterMethod
    public void tearDown() {
        lexeme = null;
        expected = null;
    }

    @Test(description = "Positive scenario of concatenation lexeme from symbols")
    public void testConcatenate() {
        assertEquals(lexeme.concatenate(), expected);
    }
}