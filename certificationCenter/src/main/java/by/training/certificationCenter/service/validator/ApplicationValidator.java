package by.training.certificationCenter.service.validator;

import by.training.certificationCenter.bean.Status;

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

    public static boolean validateFileExtension(final String fileName) {
        int pointInd = fileName.lastIndexOf(".");
        String fileExtension = fileName.substring(pointInd + 1);
        switch (fileExtension.toLowerCase()) {
            case "txt":
            case "doc":
            case "docx":
            case "pdf":
            case "jpg":
            case "png":
            case "pps":
            case "ppt":
                return true;
        }
        return false;
    }

    public static boolean checkPossibilityToUpdate(final Status status) {
        switch (status) {
            case REJECTED:
            case APPROVED:
                return false;
        }
        return true;
    }
}
