package com.project.blogapp.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

    private String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        String fileType = file.getContentType();
        for (String supportedType : this.types) {
            if (fileType.contains(supportedType)) {
                return true;
            }
        }
        return false;
    }
}
