package by.training.certificationCenter.service.validator;

import by.training.certificationCenter.bean.Status;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class ApplicationValidatorTest {

    @DataProvider(name = "dataForValidateAddedDate")
    public Object[][] createData_ForValidateAddedDate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);
        return new Object[][]{
                {today, true},
                {tomorrow, false},
                {yesterday, false}
        };
    }

    @DataProvider(name = "dataForValidateProductCode")
    public Object[][] createData_ForValidateProductCode() {
        return new Object[][]{
                {8479899708l, true},
                {850140l, true},
                {8608, true},
                {1, false},
                {12, false},
                {123, false},
                {12345678910l, false},
                {-8608, false}
        };
    }

    @DataProvider(name = "dataForValidateFileExtension")
    public Object[][] createData_ForValidateFileExtension() {
        return new Object[][]{
                {"file.txt", true},
                {"file.doc", true},
                {"file.docx", true},
                {"file.pdf", true},
                {"file.jpg", true},
                {"file.png", true},
                {"file.pps", true},
                {"file.ppt", true},
                {"file.avi", false},
                {"file.mp3", false}
        };
    }

    @DataProvider(name = "dataForCheckPossibilityToUpdate")
    public Object[][] createData_ForCheckPossibilityToUpdate() {
        return new Object[][]{
                {Status.APPROVED, false},
                {Status.REJECTED, false},
                {Status.CONSIDERATION, true},
                {Status.REGISTRATION, true}
        };
    }

    @Test(description = "positive and negative scenario of verification application date",
            dataProvider = "dataForValidateAddedDate")
    public void testValidateAddedDate(LocalDate date, boolean expected) {
        boolean actual = ApplicationValidator.validateAddedDate(date);
        assertEquals(actual, expected);
    }

    @Test(description = "positive and negative scenario of verification product code",
            dataProvider = "dataForValidateProductCode")
    public void testValidateProductCode(long code, boolean expected) {
        boolean actual = ApplicationValidator.validateProductCode(code);
        assertEquals(actual, expected);
    }

    @Test(description = "positive scenario of verification file extension",
            dataProvider = "dataForValidateFileExtension")
    public void testValidateFileExtension(String fileName, boolean expected) {
        boolean actual = ApplicationValidator.validateFileExtension(fileName);
        assertEquals(actual, expected);
    }

    @Test(description = "positive scenario of checking possibility to update",
            dataProvider = "dataForCheckPossibilityToUpdate")
    public void testCheckPossibilityToUpdate(Status status, boolean expected) {
        boolean actual = ApplicationValidator.checkPossibilityToUpdate(status);
        assertEquals(actual, expected);
    }
}