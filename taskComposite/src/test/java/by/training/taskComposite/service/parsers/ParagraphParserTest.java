package by.training.taskComposite.service.parsers;

import by.training.taskComposite.bean.Text;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ParagraphParserTest {
    ParagraphParser parser;
    String partInitial;
    String expected;
    Text text;

    @BeforeMethod
    public void setUp() {
        parser = new ParagraphParser();
        text = new Text();
        partInitial = "   In post   mean shot ye! There out her child 'sir' his lived.\n "
                + "   Design at uneasy me   season of branch on praise esteem? Abilities\n "
                + "discourse believing consisted, remaining to no?!\n "
                + "   By...";
        expected = "\tIn post mean shot ye! There out her child 'sir' his lived.\r\n"
                + "\tDesign at uneasy me season of branch on praise esteem? Abilities "
                + "discourse believing consisted, remaining to no?!\r\n"
                + "\tBy...";
    }

    @AfterMethod
    public void tearDown() {
        parser = null;
        text = null;
        partInitial = null;
        expected = null;
    }

    @Test(description = "Positive scenario of parsing a part of string")
    public void testParse() {
        parser.parse(text, partInitial);
        String actual = text.concatenate();
        assertEquals(actual, expected);
    }
}