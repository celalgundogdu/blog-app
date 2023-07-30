package com.project.blogapp.annotation;

import com.project.blogapp.constant.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {

    String message() default Messages.User.INVALID_PASSWORD;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
