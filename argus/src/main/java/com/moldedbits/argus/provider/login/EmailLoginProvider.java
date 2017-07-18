package com.moldedbits.argus.provider.login;

import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ForgotPasswordFragment;
import com.moldedbits.argus.R;
import com.moldedbits.argus.logger.ArgusLogger;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.validations.RegexValidation;
import com.moldedbits.argus.validations.ValidationEngine;

import lombok.Setter;

/**
 * Allow user to login with email and password
 */
public abstract class EmailLoginProvider extends BaseProvider {

    private static final String TAG = "EmailLoginProvider";

    private EditText usernameInput;
    private EditText passwordInput;
    private ImageView ivShowPassword;

    @Setter
    private boolean showPasswordEnabled;

    public EmailLoginProvider() {
        validationEngine = new ValidationEngine();
    }

    @Nullable
    @Override
    public View inflateView(ViewGroup parentView) {
        getValidationEngine()
                .addEmailValidation(new RegexValidation(Patterns.EMAIL_ADDRESS.pattern(),
                        context.getString(R.string.invalid_email)));

        if (context != null) {
            View loginView = LayoutInflater.from(context)
                    .inflate(R.layout.login_email, parentView, false);

            usernameInput = (EditText) loginView.findViewById(R.id.username);
            passwordInput = (EditText) loginView.findViewById(R.id.password);

            if (showPasswordEnabled) {
                ivShowPassword = (ImageView) loginView.findViewById(R.id.iv_show_pwd);
                ivShowPassword.setVisibility(View.VISIBLE);
                if (ivShowPassword != null) {
                    ivShowPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toggleShowPwd();
                        }
                    });
                }
            }

            if (Argus.getInstance().getForgotPasswordProvider() == null) {
                loginView.findViewById(R.id.tv_forgot_password).setVisibility(View.GONE);
            } else {
                loginView.findViewById(R.id.tv_forgot_password).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showForgotPasswordFragment();
                            }
                        });
            }

            theme = Argus.getInstance().getArgusTheme();

            themeHelper.applyTheme(loginView, theme);

            return loginView;
        } else {
            throw new RuntimeException("Context cannot be null");
        }
    }

    @Override
    public void performAction() {
        if (validateInput() && resultListener != null) {
            doServerLogin(usernameInput.getText().toString(), passwordInput.getText().toString());
        }
    }

    private boolean validateInput() {
        if (validationEngine == null) {
            ArgusLogger.w(TAG, "ValidationEngine is null not validating SignUp form");
            return true;
        }

        // we want to run all validations
        boolean result1 = ValidationEngine.validateEditText(usernameInput, validationEngine);
        boolean result2 = ValidationEngine.validateEditText(passwordInput, validationEngine);

        return result1 && result2;
    }

    @Override
    public int getContainerId() {
        return R.id.container_email;
    }

    protected abstract void doServerLogin(String username, String password);

    private void showForgotPasswordFragment() {
        fragment.getFragmentManager()
                .beginTransaction()
                .replace(R.id.argus_content, ForgotPasswordFragment.newInstance())
                .commit();
    }

    protected void toggleShowPwd() {
        if (passwordInput.getTransformationMethod() != null) {
            passwordInput.setTransformationMethod(null);
            passwordInput.setSelection(passwordInput.getText().length());
            ivShowPassword.setImageResource(R.drawable.ic_hide_pwd);
        } else {
            passwordInput.setTransformationMethod(new PasswordTransformationMethod());
            passwordInput.setSelection(passwordInput.getText().length());
            ivShowPassword.setImageResource(R.drawable.icn_show_pwd);
        }
    }
}
