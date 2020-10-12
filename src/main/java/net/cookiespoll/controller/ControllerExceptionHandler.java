package net.cookiespoll.controller;

import net.cookiespoll.exception.*;
import net.cookiespoll.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
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
    private final static String USER_ROLE = "user role";
    private final static String NO_FIELD = "NO FIELD";
    private final static String COOKIE_NAME_FIELD = "name";
    private final static String EMAIL_DOMEN = "email domen";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(HttpServletRequest req, Exception ex) {
        LOGGER.error("Request: {} raised exception {} ", req.getRequestURL(), ex);

        return createOneFieldErrorResponse(NO_FIELD, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        LOGGER.error("Request: {} raised exception {} ", req.getRequestURL(), ex);

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        return createErrorResponseFromList(errors);
    }

    @ExceptionHandler(FileValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleFileAddingException (HttpServletRequest req, FileValidationException ex) {
        LOGGER.error("Request: {} raised exception {} ", req.getRequestURL(), ex);
        return createOneFieldErrorResponse(FILE_FIELD, ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBindException(HttpServletRequest req, BindException ex) {
        LOGGER.error("Request: {} raised exception {} ", req.getRequestURL(), ex);

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        return createErrorResponseFromList(errors);
    }

    @ExceptionHandler(CookieRateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCookieRateException (HttpServletRequest req, CookieRateException ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised exception " + ex);

        return createOneFieldErrorResponse(NO_FIELD, ex.getMessage());
    }

    @ExceptionHandler(UserRoleValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleUserRoleException(HttpServletRequest req, UserRoleValidationException ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised exception " + ex);

        return createOneFieldErrorResponse(USER_ROLE, ex.getMessage());
    }

    @ExceptionHandler(NotUniqueCookieNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNotUniqueCookieNameException(HttpServletRequest req, NotUniqueCookieNameException ex) {
        LOGGER.error("Request: {} raised exception {} ", req.getRequestURL(), ex);
        return createOneFieldErrorResponse(COOKIE_NAME_FIELD, ex.getMessage());
    }

    @ExceptionHandler(EmailDomenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleEmailDomenException(HttpServletRequest req, EmailDomenException ex) {
        LOGGER.error("Request: {} raised exception {} ", req.getRequestURL(), ex);

        return createOneFieldErrorResponse(EMAIL_DOMEN, ex.getMessage());
    }

    private ErrorResponse createErrorResponseFromList(List<FieldError> errors) {
        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        errors.forEach(error -> createErrorDetails(error.getField(), error.getDefaultMessage()));
        return createErrorResponse(errorDetails);
    }

    private ErrorResponse createOneFieldErrorResponse(String fieldName, String errorMessage) {
        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        errorDetails.add(createErrorDetails(fieldName, errorMessage));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);

        return errorResponse;
    }

    private ErrorResponse.ErrorDetails createErrorDetails(String fieldName, String errorMessage) {
        ErrorResponse.ErrorDetails error = new ErrorResponse.ErrorDetails();
        error.setFieldName(fieldName);
        error.setMessage(errorMessage);
        return error;
    }

    private ErrorResponse createErrorResponse(List<ErrorResponse.ErrorDetails> errorDetails) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);

        return errorResponse;
    }
}



