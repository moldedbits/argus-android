package com.moldedbits.argus.provider.signup;

import android.content.Intent;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moldedbits.argus.State;
import com.moldedbits.argus.R;
import com.moldedbits.argus.logger.ArgusLogger;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.validations.RegexValidation;
import com.moldedbits.argus.validations.ValidationEngine;

public abstract class EmailSignupProvider extends BaseProvider implements
        EmailVerificationFragment.EmailVerificationListener {

    private static final String TAG = "EmailSignupProvider";
    private static final String KEY_STATE = "email_signup_provider_state";

    private EditText usernameEt;
    private EditText emailEt;
    private EditText passwordEt;
    private boolean isValidationRequired;

    public EmailSignupProvider(boolean isValidationRequired) {
        this.isValidationRequired = isValidationRequired;
        validationEngine = new ValidationEngine();
    }

    @Override
    protected void performLogin() {
        if (validate()) {
            doServerSignup(usernameEt.getText().toString(), emailEt.getText().toString(),
                           passwordEt.getText().toString());
        }
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        if (context != null) {
            getValidationEngine()
                    .addEmailValidation(new RegexValidation(Patterns.EMAIL_ADDRESS.pattern(),
                                                            context.getString(
                                                                    R.string.invalid_email)));
        }

        View signUpView = LayoutInflater.from(context)
                .inflate(R.layout.signup_email, parentView, false);
        usernameEt = (EditText) signUpView.findViewById(R.id.username);
        emailEt = (EditText) signUpView.findViewById(R.id.email);
        passwordEt = (EditText) signUpView.findViewById(R.id.password);
        return signUpView;
    }

    private boolean validate() {
        if (validationEngine == null) {
            ArgusLogger.w(TAG, "ValidationEngine is null not validating SignUp form");
            return true;
        }

        // we want to run all validations
        boolean result1 = ValidationEngine.validateEditText(usernameEt, validationEngine);
        boolean result2 = ValidationEngine.validateEditText(passwordEt, validationEngine);
        boolean result3 = ValidationEngine.validateEditText(emailEt, validationEngine);

        return result1 && result2 && result3;
    }

    @Override
    public int getContainerId() {
        return R.id.container_email;
    }

    //
    @Override
    public void startValidationActivity() {
        fragment.startActivity(new Intent(fragment.getActivity(), ValidationActivity.class));
    }

    @Override
    public void onValidated() {
        if (resultListener != null) {
            resultListener.onSuccess(State.SIGNED_IN);
        }
    }

    @Override
    public void onCancelled() {
        if (resultListener != null) {
            resultListener.onSuccess(State.SIGNED_OUT);
        }
    }

    protected void intiateSignin() {
        if (isValidationRequired) {
            startValidationActivity();
            return;
        }
        if (resultListener != null) {
            resultListener.onSuccess(State.SIGNED_IN);
        }
    }

    public abstract void doServerSignup(String username, String email, String password);
}
