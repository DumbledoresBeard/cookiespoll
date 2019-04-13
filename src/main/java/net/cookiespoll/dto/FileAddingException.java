package net.cookiespoll.dto;

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
public class FileAddingException extends Throwable {
    private final String FIELDNAME = "File";

    @ResponseBody
    public ErrorResponse getException(BindingResult result) {

        List<ObjectError> errors = result.getAllErrors();

        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        for (ObjectError ObjectError : errors) {
            ErrorResponse.ErrorDetails error = new ErrorResponse.ErrorDetails();
            error.setFieldName(FIELDNAME);
            error.setMessage(ObjectError.getDefaultMessage());
            errorDetails.add(error);
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);

        return errorResponse;


    }

}
