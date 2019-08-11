package com.helvetica.controller.validators;

import java.util.Objects;
import java.util.ResourceBundle;

public class RangeLengthValidator  extends Validator<String>{
    private final SimpleResult FAILED;
//    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private Integer minLength;
    private Integer maxLength;

    public RangeLengthValidator(Validator<String> next) {
        super(next);
        this.minLength=8;
        this.maxLength=50;
        FAILED= new SimpleResult("non valid range", false);
    }

    public RangeLengthValidator(Integer min, Integer max) {
        this.minLength = min;
        this.maxLength = max;
        FAILED = new SimpleResult(
//                resourceBundle.getString("valid.in_range") +
                " [" + minLength + ";" + maxLength + "]!",false);
    }

    public RangeLengthValidator(Integer min, Integer max, Validator<String> next) {
        super(next);
        this.minLength = min;
        this.maxLength = max;
        FAILED = new SimpleResult(
//                resourceBundle.getString("valid.in_range") +
                " [" + minLength + ";" + maxLength + "]!",false);
    }

    @Override
    public Result validate(String input) {
        if((input.length() >= minLength && input.length() <= maxLength) && Objects.nonNull(nextValidator))
            return nextValidator.validate(input);
        else if((input.length() >= minLength && input.length() <= maxLength))
            return Validator.OK;

        return FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
