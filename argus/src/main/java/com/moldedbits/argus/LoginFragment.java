package com.moldedbits.argus;

import android.os.Bundle;

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
