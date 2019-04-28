package net.cookiespoll.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final static String FILE_FIELD = "file";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised exception " + ex);

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        for (FieldError fieldError : errors) {
            ErrorResponse.ErrorDetails error = new ErrorResponse.ErrorDetails();
            error.setFieldName(fieldError.getField());
            error.setMessage(fieldError.getDefaultMessage());
            errorDetails.add(error);
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);

        return errorResponse;


    }

    @ExceptionHandler(FileValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleFileAddingException (HttpServletRequest req, FileValidationException ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised exception " + ex);

        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        ErrorResponse.ErrorDetails error = new ErrorResponse.ErrorDetails();
        error.setFieldName(FILE_FIELD);
        error.setMessage(ex.getMessage());
        errorDetails.add(error);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);

        return errorResponse;
    }

    @ExceptionHandler(ParametersValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleParametersValidationException (HttpServletRequest req, ParametersValidationException ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised exception " + ex);


        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        ErrorResponse.ErrorDetails error = new ErrorResponse.ErrorDetails();
        error.setFieldName(ex.getMessage());
        error.setMessage(ex.getMessage());
        errorDetails.add(error);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);

        return errorResponse;
    }


}
