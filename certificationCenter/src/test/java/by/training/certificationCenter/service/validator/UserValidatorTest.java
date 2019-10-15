package by.training.certificationCenter.service.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UserValidatorTest {

    @DataProvider(name = "dataForValidateEmail")
    public Object[][] createData_ForValidateEmail() {
        return new Object[][]{
                {"info@example.com", true},
                {"name@organisation.by", true},
                {"info", false},
                {"info@example", false},
                {"@example.com", false},
                {"info.com", false},
                {".com", false}
        };
    }

    @DataProvider(name = "dataForValidatePassword")
    public Object[][] createData_ForValidatePassword() {
        return new Object[][]{
                {"qwe123", true},
                {"qwerty1", true},
                {"qWE123", true},
                {"qwe#12", true},
                {"QWE123", false},
                {"qwerty", false},
                {"qweQWE", false},
                {"123456", false},
                {"qwe12", false}
        };
    }

    @DataProvider(name = "dataForValidateUNP")
    public Object[][] createData_ForValidateUNP() {
        return new Object[][]{
                {123456789, true},
                {12345678, false},
                {1234567891, false},
                {-123456789, false}
        };
    }

    @DataProvider(name = "dataForValidatePhoneNumber")
    public Object[][] createData_ForValidatePhoneNumber() {
        return new Object[][]{
                {375171231212l, true},
                {375291231212l, true},
                {79219388856l, true},
                {-375171231212l, false},
                {1234567891l, false},
                {1234567891234l, false}
        };
    }

    @Test(description = "positive scenario of verification email",
            dataProvider = "dataForValidateEmail")
    public void testValidateEmail(String email, boolean expected) {
        boolean actual = UserValidator.validateEmail(email);
        assertEquals(actual, expected);
    }

    @Test(description = "positive scenario of verification password",
            dataProvider = "dataForValidatePassword")
    public void testValidatePassword(String password, boolean expected) {
        boolean actual = UserValidator.validatePassword(password);
        assertEquals(actual, expected);
    }

    @Test(description = "positive scenario of verification UNP",
            dataProvider = "dataForValidateUNP")
    public void testValidateUNP(int unp, boolean expected) {
        boolean actual = UserValidator.validateUNP(unp);
        assertEquals(actual, expected);
    }

    @Test(description = "positive scenario of verification phone number",
            dataProvider = "dataForValidatePhoneNumber")
    public void testValidatePhoneNumber(long phone, boolean expected) {
        boolean actual = UserValidator.validatePhoneNumber(phone);
        assertEquals(actual, expected);
    }
}