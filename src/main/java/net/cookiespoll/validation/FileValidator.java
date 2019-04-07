package net.cookiespoll.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements
        ConstraintValidator<FileConstraint, MultipartFile> {

    @Override
    public void initialize(FileConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile.getContentType().equals("image/jpeg") || multipartFile.getContentType().equals("image/jpeg")
                || multipartFile.getContentType().equals("image/png");
    }
}
