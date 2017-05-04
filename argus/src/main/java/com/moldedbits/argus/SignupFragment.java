package com.moldedbits.argus;

import android.os.Bundle;

import com.moldedbits.argus.provider.BaseProvider;

import java.util.List;

public class SignupFragment extends BaseFragment {

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutContainerId() {
        return R.id.signup_container;
    }

    @Override
    protected int getLayoutId() {
        if (Argus.getInstance().getSignupLayout() != 0) {
            return Argus.getInstance().getSignupLayout();
        }
        return R.layout.fragment_signup;
    }

    @Override
    protected List<BaseProvider> getProviders() {
        return Argus.getInstance().getSignupProviders();
    }
}
