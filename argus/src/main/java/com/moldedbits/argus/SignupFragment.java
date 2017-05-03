package com.moldedbits.argus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.listener.SignUpListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.SignupProvider;

public class SignupFragment extends Fragment implements SignUpListener {

    private SignUpListener listener;

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        ViewGroup signupContainer = (ViewGroup) view.findViewById(R.id.signup_container);
        SignupProvider provider = Argus.getInstance().getSignupProviders();
        signupContainer.addView(provider.signUpView(this, signupContainer, this));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpListener) {
            listener = (SignUpListener) context;
        } else {
            throw new RuntimeException(context.toString()
                                               + " must implement SignupListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onSignupSuccess(ArgusUser user) {
        listener.onSignupSuccess(user);
    }

    @Override
    public void onSignupFailure() {
        listener.onSignupFailure();
    }

}
