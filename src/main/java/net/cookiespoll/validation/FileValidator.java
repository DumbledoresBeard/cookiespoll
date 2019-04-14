package net.cookiespoll.validation;

import net.cookiespoll.exception.FileAddingException;
import org.springframework.web.multipart.MultipartFile;


public class FileValidator {

    public final String JPGIMAGE = "image/jpg";
    public final String JPEGIMAGE = "image/jpeg";
    public final String PNGIMAGE = "image/png";


    public void validate(MultipartFile multipartFile) throws FileAddingException {
        if (multipartFile == null) {
            throw new FileAddingException("File is null, please, upload a file");
        }
        if (multipartFile.isEmpty()) {
            throw new FileAddingException("File is empty, please, upload jpg, jpeg or png file");
        }
        if (!(multipartFile.getContentType().equals(JPGIMAGE) || multipartFile.getContentType().equals(JPEGIMAGE)
                || multipartFile.getContentType().equals(PNGIMAGE))) {
           throw new FileAddingException("File type is not supported, valid file types: jpg, jpeg or png");
        }
    }
}
