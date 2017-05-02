package com.moldedbits.argus.provider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.R;
import com.moldedbits.argus.model.ArgusUser;

public abstract class SignupProvider {

    private SignUpListener signupListener;
    Fragment fragment;
    protected Context context;

    public View signUpView(Fragment fragment, ViewGroup parentView, SignUpListener listener) {
        this.signupListener = listener;
        this.fragment = fragment;
        this.context = fragment.getContext();

        View view = inflateSignUpView(parentView);
        if (view.findViewById(getSignUpButtonId()) == null) {
            throw new RuntimeException("SignuProvider view needs a button with id R.id.signup");
        }

        view.findViewById(getSignUpButtonId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
            }
        });

        return view;

    }

    protected abstract void performSignUp();

    private int getSignUpButtonId() {
        return R.id.signup;
    }

    protected abstract View inflateSignUpView(ViewGroup parentView);

    protected void callSignUpApi(ArgusUser user) {
        Argus.getInstance().loginUser(user);
        signupListener.startSignup();
    }
}
