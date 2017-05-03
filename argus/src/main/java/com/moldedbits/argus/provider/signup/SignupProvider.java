package com.moldedbits.argus.provider.signup;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.R;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;

public abstract class SignupProvider {

    public static final int DEFAULT_SIGNUP_CONTAINER_ID = -1;
    private LoginListener listener;
    Fragment fragment;
    protected Context context;

    public View signUpView(Fragment fragment, ViewGroup parentView, LoginListener listener) {
        this.listener = listener;
        this.fragment = fragment;
        this.context = fragment.getContext();

        View view = inflateSignUpView(parentView);
        if (view.findViewById(getSignUpButtonId()) == null) {
            throw new RuntimeException("SignupProvider view needs a button with id R.id.signup");
        }

        view.findViewById(getSignUpButtonId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
            }
        });

        return view;

    }

    protected void onSignupSuccess(ArgusUser user) {
        listener.onSuccess(user);
    }

    protected void onSignupFailure(String message) {
        listener.onFailure(message);
    }

    protected abstract void performSignUp();

    /**
     * Override this if you want to listen to the onActivityResult of the parent fragment
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private int getSignUpButtonId() {
        return R.id.signup;
    }

    protected abstract View inflateSignUpView(ViewGroup parentView);

    public int getContainerId() {
        return DEFAULT_SIGNUP_CONTAINER_ID;
    }

}
