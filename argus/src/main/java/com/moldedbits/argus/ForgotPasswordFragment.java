package com.moldedbits.argus;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.provider.BaseProvider;

public class ForgotPasswordFragment extends Fragment implements ResultListener {

    private ResultListener resultListener;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        BaseProvider provider = Argus.getInstance().getForgotPasswordProvider();
        provider.setResultListener(this);
        View containerView = view.findViewById(provider.getContainerId());
        if (containerView == null || !(containerView instanceof ViewGroup)) {
            throw new RuntimeException("Did you forget to define container in your layout");
        }

        ((ViewGroup) view)
                .addView(provider.providerView(this, (ViewGroup) containerView));
        return containerView;
    }

    @Override
    public void onSuccess(ArgusState argusState) {
        resultListener.onSuccess(argusState);
    }

    @Override
    public void onFailure(String message) {
        resultListener.onFailure(message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ResultListener) {
            resultListener = (ResultListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ResultListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resultListener = null;
    }

    private int getLayoutId() {
        if (Argus.getInstance().getForgotPasswordLayout() != 0) {
            return Argus.getInstance().getLoginLayout();
        }
        return R.layout.fragment_forgot_password;
    }
}
