package com.moldedbits.argus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.provider.BaseProvider;

import java.util.List;

public class SignupFragment extends BaseFragment {

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
        View view = inflater.inflate(getLayoutId(), container, false);

        ViewGroup signupContainer = (ViewGroup) view.findViewById(R.id.signup_container);
        setView(view, signupContainer, Argus.getInstance().getSignupProviders());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<BaseProvider> providers = Argus.getInstance().getSignupProviders();
        onProviderActivityResult(requestCode, resultCode, data, providers);
    }

    private int getLayoutId() {
        if (Argus.getInstance().getSignupLayout() != 0) {
            return Argus.getInstance().getSignupLayout();
        }
        return R.layout.fragment_signup;
    }
}
