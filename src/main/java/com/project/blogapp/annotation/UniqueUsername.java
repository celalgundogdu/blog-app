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
@Constraint(validatedBy = {UniqueUsernameValidator.class})
public @interface UniqueUsername {

    String message() default Messages.User.DUPLICATE_USERNAME;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
