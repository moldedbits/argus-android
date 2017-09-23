package com.moldedbits.argus.validations;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.moldedbits.argus.logger.ArgusLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abhishek
 * on 09/05/17.
 */

public class ValidationEngine {
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String REQUIRED_KEY = "required";
    public static final String NAME_KEY = "name";
    private static final String TAG = "ValidationEngine";
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
            validators.put(key, validations);
        } else {
            validators.get(key).addAll(validations);
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

    public ValidationEngine addRequiredValidation(Validation validation) {
        return addValidation(REQUIRED_KEY, validation);
    }

    public ValidationEngine addNameValidation(Validation validation) {
        return addValidation(NAME_KEY, validation);
    }

    public ValidationEngine addNameValidations(List<Validation> passwordValidations) {
        return addValidations(NAME_KEY, passwordValidations);
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
                errors += errors.isEmpty() ? rule.getErrorMessage() : "\n" + rule.getErrorMessage();
            }
        }

        return errors;
    }

    public static boolean validateEditText(final EditText editText, final ValidationEngine validationEngine) {
        if(editText.getTag() == null) {
            ArgusLogger.w(TAG, "Not performing validations for this EditText");
        }

        boolean allWell = true;

        // get validations for tag
        List<Validation> validations = validationEngine.getValidationsByKey(editText.getTag().toString());
        if(validations != null && !validations.isEmpty()) {
            String errors = ValidationEngine.validate(editText.getText().toString(), validations);
            if(!TextUtils.isEmpty(errors)) {
                editText.setError(errors);
                allWell = false;
            }
        }

        return allWell;
    }

    public boolean isValidatorAdded(String validationKey) {
        return validators.containsKey(validationKey);
    }
}
