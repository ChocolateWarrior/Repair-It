package com.helvetica.controller.validators;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.ResourceBundle;

public class PositiveValidator extends Validator<String> {

    private final SimpleResult FAILED;
//    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private BigDecimal minValue;
    private BigDecimal maxValue;

    public PositiveValidator(Validator<String> next) {
        super(next);
        this.minValue=new BigDecimal(0);
        this.maxValue=new BigDecimal(100000);
        FAILED= new SimpleResult("non valid value", false);
    }

    public PositiveValidator(BigDecimal min, BigDecimal max) {
        this.minValue = min;
        this.maxValue = max;
        FAILED = new SimpleResult(
//                resourceBundle.getString("valid.positive_range") +
                " [" + minValue + ";" + maxValue + "]!",false);
    }

    public PositiveValidator(BigDecimal min, BigDecimal max, Validator<String> next) {
        super(next);
        this.minValue = min;
        this.maxValue = max;
        FAILED = new SimpleResult(
                "The number must be in range: [" + minValue + ";" + maxValue + "]!",false);
    }

    @Override
    public Result validate(String in) {

        BigDecimal input = new BigDecimal(in);
        if((input.compareTo(minValue) > 0 && input.compareTo(maxValue) <= 0) && Objects.nonNull(nextValidator))
            return nextValidator.validate(in);
        else if((input.compareTo(minValue) > 0 && input.compareTo(maxValue) <= 0))
            return Validator.OK;

        return FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
