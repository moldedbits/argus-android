package com.moldedbits.argus.provider;

import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusTheme;
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

        getValidationEngine().addEmailValidation(new RegexValidation(
                        Patterns.EMAIL_ADDRESS.pattern(), context.getString(R.string.invalid_email)));

        if (context != null) {
            View forgotPasswordView = LayoutInflater.from(context)
                    .inflate(R.layout.forgot_password, parentView, false);

            emailInput = (EditText) forgotPasswordView.findViewById(R.id.email);
            applyTheme(forgotPasswordView);

            return forgotPasswordView;
        } else {
            throw new RuntimeException("Context cannot be null");
        }
    }

    private void applyTheme(View view) {
        ArgusTheme theme = Argus.getInstance().getArgusTheme();

        if(theme.getButtonDrawable() != 0) {
            Button actionButton = (Button) view.findViewById(R.id.action_button);
            if(actionButton != null) {
                actionButton.setBackgroundResource(theme.getButtonDrawable());
            }
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
            ArgusLogger.w(TAG, "ValidationEngine is null not validating Email field");
            return true;
        }

        return ValidationEngine.validateEditText(emailInput, validationEngine);
    }

    @Override
    public int getContainerId() {
        return R.id.container_forgot_password;
    }

    public abstract void sendPasswordResetEmail(String email);

    protected void showSuccessDialog(String s) {
        //TODO need to create dialogbox or can be override in app dialog according to app.
        Snackbar snackbar = Snackbar.make(emailInput, s, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView textView= (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    //Made the snackbar view 2 lines so it can handle lengthy error messages from server
    protected void showFailureDialog(String s) {
        //TODO need to create dialog box for handling error messages
        Snackbar snackbar = Snackbar.make(emailInput, s, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView textView= (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(2);
        snackbar.show();
    }
}
