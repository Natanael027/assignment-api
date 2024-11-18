package com.assignment.transaction.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Custom annotation for file type validation
@Constraint(validatedBy = FileTypeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageType {

    String message() default "Invalid image type. Only .jpg and .png are allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
