package by.training.taskComposite.bean;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WordTest {
    Word word;
    String expected;

    @BeforeMethod
    public void setUp() {
        word = new Word();
        Symbol[] symbols = new Symbol[]{
                new Symbol('T'), new Symbol('e'),
                new Symbol('s'), new Symbol('t')
        };
        for (Symbol symbol : symbols) {
            word.add(symbol);
        }
        expected = "Test";
    }

    @AfterMethod
    public void tearDown() {
        word = null;
        expected = null;
    }

    @Test(description = "Positive scenario of concatenation word from symbols")
    public void testConcatenate() {
        assertEquals(word.concatenate(), expected);
    }
}