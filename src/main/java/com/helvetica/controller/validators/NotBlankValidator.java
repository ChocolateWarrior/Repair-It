package com.helvetica.controller.validators;

import java.util.Objects;
import java.util.ResourceBundle;

public class NotBlankValidator extends Validator<String> {

    private final SimpleResult FAILED;
//    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public NotBlankValidator() {
        FAILED = new SimpleResult(
//                resourceBundle.getString("valid.non_blank"),
                "NO",
                false);
    }

    public NotBlankValidator(Validator<String> next) {
        super(next);
        FAILED = new SimpleResult(
//                resourceBundle.getString("valid.non_blank"),
                "NO",
                false);
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
