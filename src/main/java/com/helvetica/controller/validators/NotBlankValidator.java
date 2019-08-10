package com.helvetica.controller.validators;

import java.util.Objects;

public class NotBlankValidator extends Validator<String> {

    private final SimpleResult FAILED;

    public NotBlankValidator() {
        FAILED = new SimpleResult("This field can not be blank!",false);
    }

    public NotBlankValidator(Validator<String> next) {
        super(next);
        FAILED = new SimpleResult("This field can not be blank!",false);
    }

    @Override
    public Result validate(String input) {
        if((input.length() > 0) && Objects.nonNull(nextValidator))
            return nextValidator.validate(input);
        else if((input.length() > 0))
            return Validator.OK;

        return FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
