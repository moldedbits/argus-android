package com.moldedbits.argus.provider.login;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusTheme;
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

            if(showPasswordEnabled) {
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

            if(Argus.getInstance().getForgotPasswordProvider() == null) {
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

            applyTheme(loginView);

            return loginView;
        } else {
            throw new RuntimeException("Context cannot be null");
        }
    }

    private void applyTheme(View view) {
        ArgusTheme theme = Argus.getInstance().getArgusTheme();
        TextView welcomeTv = (TextView)view.findViewById(R.id.tv_welcome_text);
        if(welcomeTv != null && !TextUtils.isEmpty(theme.getWelcomeText())) {
            welcomeTv.setText(theme.getWelcomeText());
            welcomeTv.setTextSize(theme.getWelcomeTextSize());
        }

        if(theme.getButtonDrawable() != 0) {
            Button actionButton = (Button) view.findViewById(R.id.action_button);
            if(actionButton != null) {
                actionButton.setBackgroundResource(theme.getButtonDrawable());
            }
        }

        if(!theme.isShowEditTextDrawables()) {
            View emailIv = view.findViewById(R.id.iv_email_et);
            if(emailIv != null) {
                emailIv.setVisibility(View.GONE);
            }

            View passwordIv = view.findViewById(R.id.iv_password_et);
            if(passwordIv != null) {
                passwordIv.setVisibility(View.GONE);
            }
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
