package com.usermanagement.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class MobileNumberValidator implements ConstraintValidator <MobileNumber, String>  {

	@Override
	public boolean isValid(String mobileNo, ConstraintValidatorContext context) {
		String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$"; // Example: +91-1234567890 or 1234567890

		if (StringUtils.isEmpty(mobileNo)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{mobileNumber.empty.message}").addConstraintViolation();
			return false;
		} else {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(mobileNo);
			if (! matcher.matches()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("{mobileNumber.invalid.message}").addConstraintViolation();
				return false;
			} else {
				return true;
			}
		}
	}

}
