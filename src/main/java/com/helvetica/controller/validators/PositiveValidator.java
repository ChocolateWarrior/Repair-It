package com.helvetica.controller.validators;

import java.math.BigDecimal;

public class PositiveValidator extends Validator<BigDecimal> {


    private final SimpleResult FAILED;

    private BigDecimal minValue;
    private BigDecimal maxValue;

    public PositiveValidator(Validator<BigDecimal> next) {
        super(next);
        this.minValue=new BigDecimal(0);
        this.maxValue=new BigDecimal(100000);
        FAILED= new SimpleResult("non valid value", false);
    }

    public PositiveValidator(BigDecimal min, BigDecimal max) {
        this.minValue = min;
        this.maxValue = max;
        FAILED = new SimpleResult("The number must be in range: [" + minValue + ";" + maxValue + "]!",false);
    }

    public PositiveValidator(BigDecimal min, BigDecimal max, Validator<BigDecimal> next) {
        super(next);
        this.minValue = min;
        this.maxValue = max;
        FAILED = new SimpleResult("The number must be in range: [" + minValue + ";" + maxValue + "]!",false);
    }

    @Override
    public Result validate(BigDecimal input) {
        return (input.compareTo(minValue) > 0 && input.compareTo(maxValue) <= 0) ? Validator.OK : FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
