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

public class SortParagraphsBySentencesAmountTest {
    SortParagraphsBySentencesAmount sort;
    List<Paragraph> paragraphs;
    String expected;

    @BeforeMethod
    public void setUp() {
        sort = new SortParagraphsBySentencesAmount();
        paragraphs = new LinkedList<>();
        String initial1 = "   Depend on circumstances. Bye...\n   Hello world!";
        Text text = new Text();
        ParagraphParser parser = new ParagraphParser();
        parser.parse(text, initial1);
        text.getTextComponentStream().forEach(p -> paragraphs.add((Paragraph) p));
        expected = "\tHello world!\r\n\tDepend on circumstances. Bye...";
    }

    @AfterMethod
    public void tearDown() {
        sort = null;
        paragraphs = null;
        expected = null;
    }

    @Test(description = "Positive scenario of sorting text components")
    public void testSortSpecifiedComparator() {
        Text text = new Text();
        sort.sortSpecifiedComparator(paragraphs);
        paragraphs.stream().forEach(p -> text.add(p));
        String actual = text.concatenate();
        assertEquals(actual, expected);
    }
}