package com.moldedbits.argus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        if (Argus.getInstance().getSignupProviders() == null && rootView != null) {
            View view2 = rootView.findViewById(R.id.tv_dont_have_account);
            if (view2 != null) {
                view2.setVisibility(View.GONE);
            }
        }
        if (rootView != null) {
            if (Argus.getInstance().isSkipLoginEnable()) {
                TextView textView = (TextView) rootView.findViewById(R.id.tv_skip_login);
                if (textView != null) {
                    String skipText = Argus.getInstance().getSkipLoginText();
                    if (Argus.getInstance().isSkipLoginEnable()) {
                        if (!TextUtils.isEmpty(skipText)) {
                            textView.setText(skipText);
                        }
                    }
                    textView.setVisibility(View.VISIBLE);
                    setSkipClick(textView);
                }
            }
        }
        return rootView;
    }

    private void setSkipClick(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSkipLogin();
            }
        });
    }

    public void onSkipLogin() {
        //TODO if needed other activity for this we can also do that later
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(getActivity()));
        getActivity().finish();
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
