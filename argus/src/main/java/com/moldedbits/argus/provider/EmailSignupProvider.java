package com.moldedbits.argus.provider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moldedbits.argus.R;
import com.moldedbits.argus.model.ArgusUser;

/**
 * Created by shishank on 01/05/17.
 */

public class EmailSignupProvider extends SignupProvider {

    private EditText username;
    private EditText email;
    private EditText password;

    @Override
    protected void performSignUp() {
        if (validateInput()) {
            ArgusUser user = new ArgusUser(username.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            callSignUpApi(user);
        }
    }

    @Override
    protected View inflateSignUpView(ViewGroup parentView) {
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
}
