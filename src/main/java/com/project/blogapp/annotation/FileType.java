package com.project.blogapp.annotation;

import com.project.blogapp.constant.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileTypeValidator.class})
public @interface FileType {

    String message() default Messages.Image.INVALID_EXTENSION;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String[] types();
}
