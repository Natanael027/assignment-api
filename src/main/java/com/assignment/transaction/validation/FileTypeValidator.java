package com.assignment.transaction.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<ValidImageType, byte[]> {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpeg", "png");

    @Override
    public void initialize(ValidImageType constraintAnnotation) {
    }

    @Override
    public boolean isValid(byte[] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0) {
            return true;
        }

        String fileExtension = getFileExtension(value);
        return ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase());
    }

    // Helper method to get the file extension from the byte array
    private String getFileExtension(byte[] fileData) {
        try {
            String fileHeader = new String(fileData, 0, 4);
            if (fileHeader.startsWith("\u0000")) {
                return "jpg";
            } else {
                return "jpeg";
            }
        } catch (Exception e) {
            return "";
        }
    }
}
