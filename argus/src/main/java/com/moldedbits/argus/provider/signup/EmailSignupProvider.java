package com.moldedbits.argus.provider.signup;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusState;
import com.moldedbits.argus.R;
import com.moldedbits.argus.logger.ArgusLogger;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.validations.ValidationEngine;

public class EmailSignupProvider extends BaseProvider implements
        EmailVerificationFragment.EmailVerificationListener {

    private enum State {
        UNSTARTED,
        VERIFICATION_PENDING
    }

    private static final String TAG = "EmailSignupProvider";
    private static final String KEY_STATE = "email_signup_provider_state";

    private EditText usernameEt;
    private EditText emailEt;
    private EditText passwordEt;

    private State state = State.UNSTARTED;

    public EmailSignupProvider() {
        validationEngine = new ValidationEngine();
    }

    @Override
    protected void performLogin() {
        if (validate()) {
            ArgusUser user = new ArgusUser(usernameEt.getText().toString());
            user.setEmail(emailEt.getText().toString());
            if (resultListener != null) {
                state = State.VERIFICATION_PENDING;
                Argus.getInstance().getStorage().putString(KEY_STATE, state.toString());
                resultListener.onSuccess(new ArgusUser("New User Welcome"), ArgusState.IN_PROGRESS);
            }
        }
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        View signUpView = LayoutInflater.from(context)
                .inflate(R.layout.signup_email, parentView, false);
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

    @Override
    public Fragment getProgressView() {
        switch (state) {
            case VERIFICATION_PENDING:
                EmailVerificationFragment emailVerificationFragment = new EmailVerificationFragment();
                emailVerificationFragment.setEmailVerificationListener(this);
                return emailVerificationFragment;
            case UNSTARTED:
            default:
                return null;
        }
    }

    @Override
    public boolean isInProgress() {
        state = State.valueOf(Argus.getInstance().getStorage()
                .getString(KEY_STATE, State.UNSTARTED.toString()));
        return state == State.VERIFICATION_PENDING;
    }

    @Override
    public void onValidated() {
        if (resultListener != null) {
            resultListener.onSuccess(new ArgusUser("Mock User"), ArgusState.SIGNED_IN);
        }
    }

    @Override
    public void onCancelled() {
        if (resultListener != null) {
            resultListener.onSuccess(null, ArgusState.SIGNED_OUT);
        }
    }
}
