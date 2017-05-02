package com.moldedbits.argus.provider;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
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
    Fragment fragment;
    protected Context context;

    public View loginView(Fragment fragment, ViewGroup parentView, LoginListener listener) {
        this.loginListener = listener;
        context = fragment.getContext();
        this.fragment = fragment;

        View view = inflateLoginView(parentView);
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

    protected void onLoginFail(String message) {
        loginListener.onLoginError(message);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    abstract protected View inflateLoginView(ViewGroup parentView);

    abstract void performLogin();

    public interface LoginListener {
        void onLogin();
        void onLoginError(String message);
    }
}
