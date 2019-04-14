package net.cookiespoll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileAddingException extends Exception {

    public FileAddingException(String message) {
        super(message);
    }


}
