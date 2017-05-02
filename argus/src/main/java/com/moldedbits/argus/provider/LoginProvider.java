package com.moldedbits.argus.provider;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.R;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;

/**
 * Provides login functionality for specific end point
 */
public abstract class LoginProvider {

    protected Context context;

    protected LoginListener loginListener;

    protected Fragment fragment;

    /**
     * Provide the login view which will be shown on the login screen for this provider
     *
     * @param fragment Login fragment. This is needed to inject activity result callbacks
     * @param parentView Parent view in which this view will be inflated.
     * @param listener Login listener
     *
     * @return Inflated view to be shown on screen
     */
    public View loginView(Fragment fragment, ViewGroup parentView, LoginListener listener) {
        this.loginListener = listener;
        this.context = fragment.getContext();
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

    /**
     * Override this if you want a custom id for your login button
     *
     * @return Login button id
     */
    protected int getLoginButtonId() {
        return R.id.login;
    }

    /**
     * Override this if you want to listen to the onActivityResult of the parent fragment
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    /**
     * Inflate your login view here
     *
     * @param parentView Parent view
     * @return Inflated view
     */
    abstract protected View inflateLoginView(ViewGroup parentView);

    /**
     * Perform login here. Implementations should take care of showing loading overlay to block
     * out UI
     */
    abstract void performLogin();


    protected void onLoginSuccess(ArgusUser user) {
        loginListener.onLoginSuccess(user);
    }

    protected void onLoginFail(String message) {
        loginListener.onLoginFailure(message);
    }
}
