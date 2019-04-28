package net.cookiespoll.validation;


import net.cookiespoll.exception.ParametersValidationException;
import org.springframework.stereotype.Component;

@Component
public class ParametersValidator {

        public void validate (String name, String description, Integer rating) throws ParametersValidationException {
            if (name != null && (name.length() > 30 || name.length() < 4)) {
                throw new ParametersValidationException("Cookie name must be between 4 and 30 characters", "name");
            }
            if (description != null && (description.isEmpty() || description.length() > 150)) {
                throw new ParametersValidationException("Cookie description must be less then 150 characters and " +
                        "cannot be empty", "description");
            }

            if (rating != null && (rating < 1 || rating > 5)) {
                throw new ParametersValidationException("Rating must a digit between 1 and 5", "rating");
            }
        }

}
