package com.moldedbits.argus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.provider.BaseProvider;

import java.util.List;

/**
 * Login Fragment
 */
public class LoginFragment extends BaseFragment {

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        ViewGroup loginContainer = (ViewGroup) view.findViewById(R.id.login_container);
        setView(view, loginContainer, getProviders());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onProviderActivityResult(requestCode, resultCode, data, getProviders());
    }

    public int getLayoutId() {
        if (Argus.getInstance().getLoginLayout() != 0) {
            return Argus.getInstance().getLoginLayout();
        }
        return R.layout.fragment_login;
    }

    @Override
    protected List<BaseProvider> getProviders() {
        return Argus.getInstance().getLoginProviders();
    }
}
