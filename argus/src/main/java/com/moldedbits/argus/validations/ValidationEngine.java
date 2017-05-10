package com.moldedbits.argus.validations;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abhishek
 * on 09/05/17.
 */

public class ValidationEngine {
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private final Map<String, List<Validation>> validators;

    public ValidationEngine() {
        validators = new HashMap<>();
    }

    private ValidationEngine addValidation(final String key, Validation validation) {
        if(!validators.containsKey(key)) {
            List<Validation> validations = new ArrayList<>();
            validations.add(validation);
            validators.put(key, validations);
        } else {
            validators.get(key).add(validation);
        }

        return this;
    }

    private ValidationEngine addValidations(final String key, List<Validation> validations) {
        if(!validators.containsKey(key)) {
            validators.put(EMAIL_KEY, validations);
        } else {
            validators.get(EMAIL_KEY).addAll(validations);
        }

        return this;
    }

    public ValidationEngine addEmailValidation(Validation validation) {
        return addValidation(EMAIL_KEY, validation);
    }

    public ValidationEngine addEmailValidations(List<Validation> emailValidations) {
        return addValidations(EMAIL_KEY, emailValidations);
    }

    public ValidationEngine addPasswordValidation(Validation validation) {
        return addValidation(PASSWORD_KEY, validation);
    }

    public ValidationEngine addPasswordValidations(List<Validation> passwordValidations) {
        return addValidations(PASSWORD_KEY, passwordValidations);
    }

    @Nullable
    public List<Validation> getValidationsByKey(String key) {
        if(validators.containsKey(key)) {
            return validators.get(key);
        }

        return null;
    }

    public static String validate(String text, List<Validation> rules) {
        String errors = "";
        for(Validation rule: rules) {
            if(!rule.validate(text)) {
                errors += "\n" + rule.getErrorMessage();
            }
        }

        return errors;
    }

}
