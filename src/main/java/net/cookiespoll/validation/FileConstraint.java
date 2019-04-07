package net.cookiespoll.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileConstraint {
    String message() default  "Invalid file type, file must be jpg, jpeg or png";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
