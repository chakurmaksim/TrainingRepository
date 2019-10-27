package by.training.certificationCenter.service.validator;

import by.training.certificationCenter.bean.Status;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class ApplicationValidatorTest {
    private LocalDate addedDate;

    @BeforeTest
    public void setUp() {
        addedDate = LocalDate.of(2019, 10, 10);
    }

    @AfterTest
    public void tearDown() {
        addedDate = null;
    }

    @DataProvider(name = "dataForValidateDateAdded")
    public Object[][] createData_ForValidateDateAdded() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);
        return new Object[][]{
                {today, true},
                {tomorrow, false},
                {yesterday, false}
        };
    }

    @DataProvider(name = "dataForValidateDateResolved")
    public Object[][] createData_ForValidateDateResolved() {
        LocalDate equalsDate = LocalDate.of(2019, 10, 10);
        LocalDate plusFifteen = equalsDate.plusDays(15);
        LocalDate plusSixteen = equalsDate.plusDays(16);
        LocalDate yesterday = equalsDate.minusDays(1);
        return new Object[][]{
                {equalsDate, true},
                {plusFifteen, true},
                {yesterday, false},
                {plusSixteen, false}
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
            dataProvider = "dataForValidateDateAdded")
    public void testValidateDateAdded(LocalDate date, boolean expected) {
        boolean actual = ApplicationValidator.validateDateAdded(date);
        assertEquals(actual, expected);
    }

    @Test(description = "positive and negative scenario of verification date of completion",
            dataProvider = "dataForValidateDateResolved")
    public void testValidateDateResolved(LocalDate resolveDate, boolean expected) {
        boolean actual = ApplicationValidator.validateDateResolved(addedDate, resolveDate);
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