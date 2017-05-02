package com.moldedbits.argus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.LoginProvider;

/**
 * Login Fragment
 */
public class LoginFragment extends Fragment implements LoginListener {

    private LoginListener listener;

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
            loginContainer.addView(provider.loginView(this, loginContainer, this));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            listener = (LoginListener) context;
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
    public void onLoginSuccess(ArgusUser user) {
        listener.onLoginSuccess(user);
    }

    @Override
    public void onLoginFailure(String message) {
        listener.onLoginFailure(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (LoginProvider provider : Argus.getInstance().getLoginProviders()) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }
}
