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
import com.moldedbits.argus.provider.BaseProvider;

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
        View view = inflater.inflate(getLayoutId(), container, false);

        ViewGroup loginContainer = (ViewGroup) view.findViewById(R.id.login_container);
        for (BaseProvider provider : Argus.getInstance().getLoginProviders()) {
            if (provider.getContainerId() == BaseProvider.DEFAULT_CONTAINER_ID) {
                loginContainer.addView(provider.loginView(this, loginContainer, this));
            } else {
                View containerView = view.findViewById(provider.getContainerId());
                if (containerView == null || !(containerView instanceof ViewGroup)) {
                    throw new RuntimeException("Did you forget to define container in your " +
                                                       "layout");
                }
                ((ViewGroup) containerView).addView(
                        provider.loginView(this, (ViewGroup) containerView, this));

            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            listener = (LoginListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onSuccess(ArgusUser user) {
        listener.onSuccess(user);
    }

    @Override
    public void onFailure(String message) {
        listener.onFailure(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (BaseProvider provider : Argus.getInstance().getLoginProviders()) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }

    public int getLayoutId() {
        if (Argus.getInstance().getLoginLayout() != 0) {
            return Argus.getInstance().getLoginLayout();
        }
        return R.layout.fragment_login;
    }
}
