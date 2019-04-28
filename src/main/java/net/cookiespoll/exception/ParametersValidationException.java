package net.cookiespoll.exception;

public class ParametersValidationException extends Exception {

    private String fieldName;

    public ParametersValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }
}
