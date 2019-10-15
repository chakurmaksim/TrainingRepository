package by.training.certificationCenter.service.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public final class ApplicationValidator {
    private ApplicationValidator() {
    }

    public static boolean validateAddedDate(final LocalDate addedDate) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter).equals(addedDate.format(formatter));
    }

    public static boolean validateProductCode(final long productCode) {
        Pattern pattern = Pattern.compile("\\d{4,10}");
        return Long.toString(productCode).matches(pattern.pattern());
    }
}
