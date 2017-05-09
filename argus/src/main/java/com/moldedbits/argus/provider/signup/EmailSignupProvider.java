package com.moldedbits.argus.provider.signup;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moldedbits.argus.R;
import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;

public class EmailSignupProvider extends BaseProvider {

    private EditText username;
    private EditText email;
    private EditText password;

    @Override
    protected void performLogin() {
        if (validateInput()) {
            ArgusUser user = new ArgusUser(username.getText().toString());
            user.setEmail(email.getText().toString());
            if (resultListener != null) {
                resultListener.onSuccess(new ArgusUser("New User Welcome"), ResultListener.ResultState.SIGN_UP);
            }
        }
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        View signUpView = LayoutInflater.from(context).inflate(R.layout.signup_email, parentView,
                                                               false);
        username = (EditText) signUpView.findViewById(R.id.username);
        email = (EditText) signUpView.findViewById(R.id.email);
        password = (EditText) signUpView.findViewById(R.id.password);
        return signUpView;
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(username.getText())) {
            username.setError(context.getString(R.string.empty_username));
            return false;
        }

        if (TextUtils.isEmpty(password.getText())) {
            password.setError(context.getString(R.string.empty_password));
            return false;
        }
        if (TextUtils.isEmpty(email.getText())) {
            email.setError(context.getString(R.string.empty_email));
            return false;
        }
        return true;
    }

    @Override
    public int getContainerId() {
        return R.id.container_email;
    }
}
