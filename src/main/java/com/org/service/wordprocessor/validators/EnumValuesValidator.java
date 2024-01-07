package com.org.service.wordprocessor.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValuesValidator implements ConstraintValidator<BelongsTo, String> {

    private List<String> list;

    @Override
    public void initialize(BelongsTo constraintAnnotation) {
        list = new ArrayList<>();
        Class<? extends Enum<?>> enumclzz = constraintAnnotation.enumclass();
        Enum[] enums = enumclzz.getEnumConstants();
        for(Enum val : enums ) {
            list.add(val.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(list.contains(s.toUpperCase())) {
            return true;
        }
        return false;
    }
}
