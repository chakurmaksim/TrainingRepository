package by.training.certificationCenter.service.validator;

import java.util.regex.Pattern;

public final class UserValidator {
    private UserValidator() {
    }

    public static boolean validateUNP(final int unp) {
        Pattern pattern = Pattern.compile("\\d{9}");
        if (Integer.valueOf(unp).toString().matches(pattern.pattern())) {
            return true;
        }
        return false;
    }

    public static boolean validatePhoneNumber(final long phone) {
        Pattern pattern = Pattern.compile("\\d{11,12}");
        if (Long.valueOf(phone).toString().matches(pattern.pattern())) {
            return true;
        }
        return false;
    }

    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile("^\\w+@\\w+\\.[a-z]{2,3}$");
        if (email.matches(pattern.pattern())) {
            return true;
        }
        return false;
    }

    public static boolean validatePassword(final String password) {
        Pattern pattern = Pattern.compile(
                "(?=.*[0-9])(?=.*[a-z])[0-9a-zA-Z!@#$%^&*]{6,}");
        if (password.matches(pattern.pattern())) {
            return true;
        }
        return false;
    }
}
