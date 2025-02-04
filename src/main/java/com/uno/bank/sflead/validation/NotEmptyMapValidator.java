package com.uno.bank.sflead.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Map;

public class NotEmptyMapValidator implements ConstraintValidator<NotEmptyMap, Map<?, ?>> {
    @Override
    public boolean isValid(Map<?, ?> map, ConstraintValidatorContext context) {
        return map != null && !map.isEmpty();
    }
}
