package com.project.blogapp.annotation;

import com.project.blogapp.constant.Regex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches(Regex.PASSWORD_REGEXP);
    }
}
