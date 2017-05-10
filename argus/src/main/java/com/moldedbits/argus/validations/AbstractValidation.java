package com.moldedbits.argus.validations;

/**
 * Created by abhishek
 * on 10/05/17.
 */

public abstract class AbstractValidation implements Validation {
    private final String errorMessage;

    AbstractValidation(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
