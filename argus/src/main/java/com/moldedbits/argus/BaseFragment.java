package com.moldedbits.argus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;

import java.util.List;


public abstract class BaseFragment extends Fragment implements ResultListener {

    private ResultListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        setView(view, getProviders());
        return view;
    }

    protected void setView(View view, List<BaseProvider> providerList) {
        for (BaseProvider provider : providerList) {

            View containerView = view.findViewById(provider.getContainerId());
            if (containerView == null || !(containerView instanceof ViewGroup)) {
                throw new RuntimeException("Did you forget to define container in your layout");
            }
            ((ViewGroup) containerView)
                    .addView(provider.loginView(this, (ViewGroup) containerView, this));
        }
    }

    @Override
    public void onSuccess(ArgusUser user, ArgusState state) {
        listener.onSuccess(user, state);
    }

    @Override
    public void onFailure(String message, ArgusState state) {
        listener.onFailure(message, state);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onProviderActivityResult(requestCode, resultCode, data, getProviders());
    }

    protected void onProviderActivityResult(int requestCode, int resultCode, Intent data,
                                            List<BaseProvider>
                                                    providerList) {
        for (BaseProvider provider : providerList) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected abstract int getLayoutId();

    protected abstract List<BaseProvider> getProviders();

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }
}
