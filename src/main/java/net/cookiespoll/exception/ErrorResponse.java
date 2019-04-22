package net.cookiespoll.exception;

import java.util.List;

public class ErrorResponse {

    private List<ErrorDetails> errors;

    public ErrorResponse() {}

    public List<ErrorDetails> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetails> errors) {
        this.errors = errors;
    }

    public static class ErrorDetails {
        private String fieldName;
        private String message;

        public ErrorDetails() {}

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
