package com.moldedbits.argus.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.R;
import com.moldedbits.argus.model.ArgusUser;

/**
 * Provides login functionality for specific end point
 */
public abstract class LoginProvider {

    private LoginListener loginListener;

    public View loginView(Context context, ViewGroup parentView, LoginListener listener) {
        this.loginListener = listener;

        View view = inflateLoginView(context, parentView);
        if (view.findViewById(getLoginButtonId()) == null) {
            throw new RuntimeException("LoginProvider view needs a button with id R.id.login");
        }

        view.findViewById(getLoginButtonId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        return view;
    }

    protected int getLoginButtonId() {
        return R.id.login;
    }

    protected void onLoginSuccess(ArgusUser user) {
        Argus.getInstance().loginUser(user);
        loginListener.onLogin();
    }

    abstract protected View inflateLoginView(Context context, ViewGroup parentView);
    abstract void performLogin();

    public interface LoginListener {
        void onLogin();
    }
}
