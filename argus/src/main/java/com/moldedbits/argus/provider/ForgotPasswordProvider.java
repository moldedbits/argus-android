package com.moldedbits.argus.provider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.moldedbits.argus.R;
import com.moldedbits.argus.logger.ArgusLogger;
import com.moldedbits.argus.validations.RegexValidation;
import com.moldedbits.argus.validations.ValidationEngine;

import static com.facebook.login.widget.ProfilePictureView.TAG;


public abstract class ForgotPasswordProvider extends BaseProvider {

    private EditText emailInput;

    public ForgotPasswordProvider() {
        validationEngine = new ValidationEngine();
    }

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

    @Override
    public int getContainerId() {
        return R.id.container_forgot_password;
    }

    public abstract void sendPasswordResetEmail(String email);

    protected void showSuccessDialog(String s){
        //TODO need to create dialogbox or can be override in app dialog according to app.
        Toast.makeText(fragment.getActivity(),s,Toast.LENGTH_SHORT).show();
    }
}
