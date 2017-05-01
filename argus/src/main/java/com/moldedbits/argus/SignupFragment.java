package com.moldedbits.argus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.provider.SignUpListener;
import com.moldedbits.argus.provider.SignupProvider;

public class SignupFragment extends Fragment implements SignUpListener {

    private LoginFragment.OnFragmentInteractionListener listener;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ViewGroup signupContainer = (ViewGroup) view.findViewById(R.id.login_container);
        for (SignupProvider provider : Argus.getInstance().getSignupProviders()) {
            signupContainer.addView(provider.signUpView(this, signupContainer, this));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            listener = (LoginFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                                               + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onSignupSuccess() {
         listener.onSignUpSuccess();
    }

    @Override
    public void onSignupError() {
        listener.onSignupError();
    }

    @Override
    public void startSignup() {
        // todo we need to call api here
        onSignupSuccess();

    }
}
