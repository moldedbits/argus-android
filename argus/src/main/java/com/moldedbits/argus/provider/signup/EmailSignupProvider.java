package com.moldedbits.argus.provider.signup;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moldedbits.argus.R;
import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.logger.ArgusLogger;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.validations.RegexValidation;
import com.moldedbits.argus.validations.ValidationEngine;

public class EmailSignupProvider extends BaseProvider {

    private static final String TAG = "EmailSignupProvider";
    private EditText usernameEt;
    private EditText emailEt;
    private EditText passwordEt;

    public EmailSignupProvider() {
        validationEngine = new ValidationEngine();
    }

    @Override
    protected void performLogin() {
        if (validate()) {
            ArgusUser user = new ArgusUser(usernameEt.getText().toString());
            user.setEmail(emailEt.getText().toString());
            if (resultListener != null) {
                resultListener.onSuccess(new ArgusUser("New User Welcome"), ResultListener.ResultState.SIGNED_UP);
            }
        }
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        if (context != null) {
            getValidationEngine().addEmailValidation(new RegexValidation(Patterns.EMAIL_ADDRESS.pattern(),
                    context.getString(R.string.invalid_email)));
        }

        View signUpView = LayoutInflater.from(context).inflate(R.layout.signup_email, parentView,
                                                               false);
        usernameEt = (EditText) signUpView.findViewById(R.id.username);
        emailEt = (EditText) signUpView.findViewById(R.id.email);
        passwordEt = (EditText) signUpView.findViewById(R.id.password);
        return signUpView;
    }

    private boolean validate() {
        if(validationEngine == null) {
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
}
