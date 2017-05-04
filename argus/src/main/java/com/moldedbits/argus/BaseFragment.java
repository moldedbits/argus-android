package com.moldedbits.argus;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;

import java.util.List;


public abstract class BaseFragment extends Fragment implements LoginListener {

    private LoginListener listener;

    protected void setView(View view, List<BaseProvider> providerList) {
        for (BaseProvider provider : providerList) {
                View containerView = view.findViewById(provider.getContainerId());
                if (containerView == null || !(containerView instanceof ViewGroup)) {
                    throw new RuntimeException("Did you forget to define container in your " +
                                                       "layout");
                }
                ((ViewGroup) containerView)
                        .addView(provider.loginView(this, (ViewGroup) containerView, this));
        }
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

    protected void onProviderActivityResult(int requestCode, int resultCode, Intent data,
                                            List<BaseProvider>
                                                    providerList) {
        for (BaseProvider provider : providerList) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }
}
