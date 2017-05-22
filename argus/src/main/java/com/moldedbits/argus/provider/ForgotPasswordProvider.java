package com.moldedbits.argus.provider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moldedbits.argus.R;
import com.moldedbits.argus.logger.ArgusLogger;
import com.moldedbits.argus.validations.RegexValidation;
import com.moldedbits.argus.validations.ValidationEngine;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by shishank on 22/05/17.
 */

public abstract class ForgotPasswordProvider extends BaseProvider {

    private EditText emailInput;

    @Override
    protected View inflateView(ViewGroup parentView) {
        if (context == null)
            return null;

        getValidationEngine()
                .addEmailValidation(new RegexValidation(Patterns.EMAIL_ADDRESS.pattern(),
                                                        context.getString(R.string.invalid_email)));

        if (context != null) {
            View loginView = LayoutInflater.from(context)
                    .inflate(R.layout.forgot_password, parentView, false);

            emailInput = (EditText) loginView.findViewById(R.id.email);
            return loginView;
        } else {
            throw new RuntimeException("Context cannot be null");
        }
    }

    @Override
    protected void performAction() {
        if (validate()) {
            sendPasswordResetEmail(emailInput.getText().toString());
        }
    }

    private boolean validate() {
        if (validationEngine == null) {
            ArgusLogger.w(TAG, "ValidationEngine is null not validating SignUp form");
            return true;
        }

        return ValidationEngine.validateEditText(emailInput, validationEngine);
    }

    public abstract void sendPasswordResetEmail(String email);
}
