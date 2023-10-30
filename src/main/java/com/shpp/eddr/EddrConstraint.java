package com.shpp.eddr;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EddrValidator.class)
public @interface EddrConstraint {
    String message() default "Eddr not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
