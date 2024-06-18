package com.usermanagement.Validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MobileNumberValidator.class)
public @interface MobileNumber {

	String message() default "{mobileNumber.invalid.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
