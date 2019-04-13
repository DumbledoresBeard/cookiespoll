package net.cookiespoll.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class FileValidator implements
        Validator {

    public final String JPGIMAGE = "image/jpg";
    public final String JPEGIMAGE = "image/jpeg";
    public final String PNGIMAGE = "image/png";


    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MultipartFile multipartFile = (MultipartFile) o;
        if (!(multipartFile.getContentType().equals(JPGIMAGE) || multipartFile.getContentType().equals(JPEGIMAGE)
                || multipartFile.getContentType().equals(PNGIMAGE))) {
            errors.reject("Filetype is not supported, valid file types: jpg, jpeg or png");
        }
    }
}
