package com.uno.bank.sflead.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyMapValidator.class)
@Documented
public @interface NotEmptyMap {
    String message() default "Map must not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
