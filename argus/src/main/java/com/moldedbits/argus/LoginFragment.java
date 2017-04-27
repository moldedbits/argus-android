package com.moldedbits.argus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.provider.LoginProvider;

/**
 * Login Fragment
 */
public class LoginFragment extends Fragment implements LoginProvider.LoginListener {

    private OnFragmentInteractionListener listener;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ViewGroup loginContainer = (ViewGroup) view.findViewById(R.id.login_container);
        for (LoginProvider provider : Argus.getInstance().getLoginProviders()) {
            loginContainer.addView(provider.loginView(getContext(), loginContainer, this));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
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
    public void onLogin() {
        listener.onLoginSuccess();
    }

    interface OnFragmentInteractionListener {
        void onLoginSuccess();
    }
}
