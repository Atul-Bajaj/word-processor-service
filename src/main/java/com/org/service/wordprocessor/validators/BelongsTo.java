package com.org.service.wordprocessor.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValuesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotEmpty(message = "Value cannot be null or empty")
@NotNull(message = "Value cannot be null")
@NotBlank(message = "Value cannot be empty")
@ReportAsSingleViolation
public @interface BelongsTo {

    Class<? extends Enum<?>> enumclass();
    String message() default "Value is empty or not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
