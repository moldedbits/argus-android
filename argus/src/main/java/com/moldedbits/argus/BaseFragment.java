package com.moldedbits.argus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;

import java.util.List;


public abstract class BaseFragment extends Fragment implements LoginListener {

    private LoginListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ViewGroup loginContainer = (ViewGroup) view.findViewById(getLayoutContainerId());
        setView(view, loginContainer, getProviders());
        return view;
    }

    protected void setView(View view, @Nullable ViewGroup container,
                           List<BaseProvider> providerList) {
        for (BaseProvider provider : providerList) {
            if (provider.getContainerId() == BaseProvider.DEFAULT_CONTAINER_ID) {
                container.addView(provider.loginView(this, container, this));
            } else {
                View containerView = view.findViewById(provider.getContainerId());
                if (containerView == null || !(containerView instanceof ViewGroup)) {
                    throw new RuntimeException("Did you forget to define container in your " +
                                                       "layout");
                }
                ((ViewGroup) containerView)
                        .addView(provider.loginView(this, (ViewGroup) containerView, this));

            }
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

    protected abstract int getLayoutContainerId();

    protected abstract int getLayoutId();

    protected abstract List<BaseProvider> getProviders();
}
