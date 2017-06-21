package com.moldedbits.argus;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if(Argus.getInstance().getSignupProviders() == null && rootView != null) {
            View view2 = rootView.findViewById(R.id.tv_dont_have_account);
            if (view2 != null) {
                view2.setVisibility(View.GONE);
            }
        }

        return rootView;
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
