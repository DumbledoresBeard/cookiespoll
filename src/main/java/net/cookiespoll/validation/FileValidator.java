package net.cookiespoll.validation;

import net.cookiespoll.exception.FileValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    public final static String JPG_IMAGE = "image/jpg";
    public final static String JPEG_IMAGE = "image/jpeg";
    public final static String PNG_IMAGE = "image/png";


    public void validate(MultipartFile multipartFile) throws FileValidationException {
        if (multipartFile == null) {
            throw new FileValidationException("File is null, please, upload a file");
        }

        if (multipartFile.isEmpty()) {
            throw new FileValidationException("File is empty, please, upload jpg, jpeg or png file");
        }

        if (!(JPG_IMAGE.equals(multipartFile.getContentType()) || JPEG_IMAGE.equals(multipartFile.getContentType())
                || PNG_IMAGE.equals(multipartFile.getContentType()))) {
           throw new FileValidationException("File type is not supported, valid file types: jpg, jpeg or png");
        }
    }
}
