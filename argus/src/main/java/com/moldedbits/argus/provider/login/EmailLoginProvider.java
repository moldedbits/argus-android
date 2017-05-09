package com.moldedbits.argus.provider.login;

import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.R;
import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;

/**
 * Allow user to login with email and password
 */
public class EmailLoginProvider extends BaseProvider {

    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;

    @Override
    public View inflateLoginView(ViewGroup parentView) {

        View loginView = LayoutInflater.from(context)
                .inflate(R.layout.login_email, parentView, false);
        if (context != null) {
            usernameInput = (TextInputEditText) loginView.findViewById(R.id.username);
            passwordInput = (TextInputEditText) loginView.findViewById(R.id.password);
            loginView.findViewById(R.id.action_button).setBackgroundColor(
                    ContextCompat.getColor(context,
                                           Argus.getInstance().getArgusTheme().getButtonColor()));
        }
        return loginView;
    }

    @Override
    public void performLogin() {
        //TODO create a TestHelper class to get mock data
        if (validateInput() && resultListener != null) {
            resultListener.onSuccess(new ArgusUser("Mock User"), ResultListener.ResultState.SIGNED_IN);
        }
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(usernameInput.getText())) {
            usernameInput.setError(context.getString(R.string.empty_username));
            return false;
        }

        if (TextUtils.isEmpty(passwordInput.getText())) {
            passwordInput.setError(context.getString(R.string.empty_password));
            return false;
        }

        return true;
    }

    @Override
    public int getContainerId() {
        return R.id.container_email;
    }
}
