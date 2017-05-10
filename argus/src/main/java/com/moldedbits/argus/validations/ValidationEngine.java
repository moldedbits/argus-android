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

    public ValidationEngine addEmailValidation(Validation validation) {
        if(!validators.containsKey(EMAIL_KEY)) {
            List<Validation> emailValidations = new ArrayList<>();
            emailValidations.add(validation);
            validators.put(EMAIL_KEY, emailValidations);
        } else {
            validators.get(EMAIL_KEY).add(validation);
        }

        return this;
    }

    public ValidationEngine addEmailValidations(List<Validation> emailValidations) {
        if(!validators.containsKey(EMAIL_KEY)) {
            validators.put(EMAIL_KEY, emailValidations);
        } else {
            validators.get(EMAIL_KEY).addAll(emailValidations);
        }

        return this;
    }

    public ValidationEngine addPasswordValidation(Validation validation) {
        if(!validators.containsKey("password")) {
            List<Validation> passwordValidations = new ArrayList<>();
            passwordValidations.add(validation);
            validators.put(EMAIL_KEY, passwordValidations);
        } else {
            validators.get(PASSWORD_KEY).add(validation);
        }

        return this;
    }

    public ValidationEngine addPasswordValidations(List<Validation> passwordValidations) {
        if(!validators.containsKey(EMAIL_KEY)) {
            validators.put(EMAIL_KEY, passwordValidations);
        } else {
            validators.get(EMAIL_KEY).addAll(passwordValidations);
        }

        return this;
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
