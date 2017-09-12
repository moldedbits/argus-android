package com.moldedbits.argus.provider.signup;

import android.content.Intent;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusState;
import com.moldedbits.argus.R;
import com.moldedbits.argus.logger.ArgusLogger;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.validations.RegexValidation;
import com.moldedbits.argus.validations.ValidationEngine;

public abstract class EmailSignupProvider extends BaseProvider {

    private static final String TAG = "EmailSignupProvider";
    private static final String KEY_STATE = "email_signup_provider_state";

    private EditText firstNameEt;
    private EditText emailEt;
    private EditText passwordEt;
    private TextView welcomeTv;
    private EditText lastNameEt;
    private boolean isValidationRequired;

    public EmailSignupProvider(boolean isValidationRequired) {
        this.isValidationRequired = isValidationRequired;
        validationEngine = new ValidationEngine();
    }

    protected EmailSignupProvider() {
    }

    @Override
    protected void performAction() {
        if (validate()) {
            doServerSignup(firstNameEt.getText().toString(), lastNameEt.getText().toString()
                    , emailEt.getText().toString(),
                    passwordEt.getText().toString());
        }
    }

    @Override
    protected View inflateView(ViewGroup parentView) {
        if (context != null) {
            getValidationEngine()
                    .addEmailValidation(new RegexValidation(Patterns.EMAIL_ADDRESS.pattern(),
                            context.getString(
                                    R.string.invalid_email)));
        }

        View signUpView = LayoutInflater.from(context)
                .inflate(R.layout.signup_email, parentView, false);
        firstNameEt = (EditText) signUpView.findViewById(R.id.fname);
        lastNameEt = (EditText) signUpView.findViewById(R.id.lname);
        emailEt = (EditText) signUpView.findViewById(R.id.email);
        passwordEt = (EditText) signUpView.findViewById(R.id.password);
        welcomeTv = (TextView) signUpView.findViewById(R.id.tv_welcome_text);

        theme = Argus.getInstance().getArgusTheme();

        themeHelper.applyTheme(signUpView, theme);
        return signUpView;
    }

    private boolean validate() {
        if (validationEngine == null) {
            ArgusLogger.w(TAG, "ValidationEngine is null not validating SignUp form");
            return true;
        }

        // we want to run all validations
        boolean result1 = ValidationEngine.validateEditText(firstNameEt, validationEngine);
        boolean result2 = ValidationEngine.validateEditText(lastNameEt, validationEngine);
        boolean result3 = ValidationEngine.validateEditText(passwordEt, validationEngine);
        boolean result4 = ValidationEngine.validateEditText(emailEt, validationEngine);

        return result1 && result2 && result3 && result4;
    }

    @Override
    public int getContainerId() {
        return R.id.container_email;
    }


    private void startValidationActivity() {
        fragment.startActivity(new Intent(fragment.getActivity(), ValidationActivity.class));
    }

    protected void onSignupSuccess() {
        if (isValidationRequired) {
            startValidationActivity();
            return;
        }
        if (resultListener != null) {
            resultListener.onSuccess(ArgusState.SIGNED_IN);
        }
    }

    public abstract void doServerSignup(String fName, String lName, String email, String password);
}
