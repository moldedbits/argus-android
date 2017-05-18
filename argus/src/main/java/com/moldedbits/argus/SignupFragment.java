package com.moldedbits.argus;

import com.moldedbits.argus.provider.BaseProvider;

import java.util.List;

public class SignupFragment extends BaseFragment {

    public static SignupFragment newInstance() {
        return new SignupFragment();
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
