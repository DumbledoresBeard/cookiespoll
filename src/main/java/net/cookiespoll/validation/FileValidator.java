package net.cookiespoll.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements
        ConstraintValidator<FileConstraint, MultipartFile> {

    public final String JPGIMAGE = "image/jpg";
    public final String JPEGIMAGE = "image/jpeg";
    public final String PNGIMAGE = "image/png";

    @Override
    public void initialize(FileConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile.getContentType().equals(JPGIMAGE) || multipartFile.getContentType().equals(JPEGIMAGE)
                || multipartFile.getContentType().equals(PNGIMAGE);
    }
}
