package by.training.taskComposite.bean;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class SentenceTest {
    Sentence sentence;
    String expected;

    @BeforeMethod
    public void setUp() {
        sentence = new Sentence();
        Symbol[] symbols1 = new Symbol[]{
                new Symbol('H'), new Symbol('e'),
                new Symbol('l'), new Symbol('l'),
                new Symbol('o')
        };
        Word firstWord = new Word();
        Arrays.stream(symbols1).forEach(s -> firstWord.add(s));
        Lexeme firstLexeme = new Lexeme();
        firstLexeme.add(firstWord);
        Symbol[] symbols2 = new Symbol[]{
                new Symbol('W'), new Symbol('o'),
                new Symbol('r'), new Symbol('l'),
                new Symbol('d')
        };
        Word secondWord = new Word();
        Arrays.stream(symbols2).forEach(s -> secondWord.add(s));
        Punctuation secondPunctuation = new Punctuation();
        secondPunctuation.add(new Symbol('!'));
        Lexeme secondLexeme = new Lexeme();
        secondLexeme.add(secondWord);
        secondLexeme.add(secondPunctuation);
        sentence.add(firstLexeme);
        sentence.add(secondLexeme);
        expected = "Hello World!";
    }

    @AfterMethod
    public void tearDown() {
        sentence = null;
        expected = null;
    }

    @Test(description = "Positive scenario of concatenation sentence from symbols")
    public void testConcatenate() {
        assertEquals(sentence.concatenate(), expected);
    }
}