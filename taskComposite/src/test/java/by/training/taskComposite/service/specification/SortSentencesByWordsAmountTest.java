package by.training.taskComposite.service.specification;

import by.training.taskComposite.bean.Paragraph;
import by.training.taskComposite.bean.Text;
import by.training.taskComposite.service.parsers.ParagraphParser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SortSentencesByWordsAmountTest {
    SortSentencesByWordsAmount sort;
    List<Paragraph> paragraphs;
    String expected;

    @BeforeMethod
    public void setUp() {
        sort = new SortSentencesByWordsAmount();
        paragraphs = new LinkedList<>();
        String initial = "   Proceed how any engaged visitor. How is life? " +
                "He is going to... Hello!";
        expected = "Hello! How is life? He is going to... Proceed how any engaged visitor.";
        Text text = new Text();
        ParagraphParser parser = new ParagraphParser();
        parser.parse(text, initial);
        text.getTextComponentStream().forEach(p -> paragraphs.add((Paragraph) p));
    }

    @AfterMethod
    public void tearDown() {
        sort = null;
        paragraphs = null;
        expected = null;
    }

    @Test(description = "Positive scenario of sorting text components")
    public void testSortSpecifiedComparator() {
        sort.sortSpecifiedComparator(paragraphs);
        String actual = paragraphs.get(0).concatenate();
        assertEquals(actual, expected);
    }
}