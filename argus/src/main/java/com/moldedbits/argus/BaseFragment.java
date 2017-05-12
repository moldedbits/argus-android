package com.moldedbits.argus;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.utils.ViewUtils;

import java.util.List;


public abstract class BaseFragment extends Fragment implements ResultListener {

    private ResultListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        setView(view, getProviders());
        applyTheme(view);
        return view;
    }

    private void applyTheme(final View view) {
        ArgusTheme theme = Argus.getInstance().getArgusTheme();
        if(theme.getLogo() != 0) {
            ImageView iv = (ImageView) view.findViewById(R.id.iv_logo);
            iv.setImageResource(theme.getLogo());
        }

        PorterDuffColorFilter filter = new PorterDuffColorFilter(ViewUtils.fetchAccentColor(getContext()), PorterDuff.Mode.MULTIPLY);
        ContextCompat.getDrawable(getContext(), R.drawable.email_icon).setColorFilter(filter);
        ContextCompat.getDrawable(getContext(), R.drawable.password_icon).setColorFilter(filter);
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

        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.container_social);
        if(viewGroup.getChildCount() == 0) {
            view.findViewById(R.id.tv_social_header).setVisibility(View.GONE);
        }

    }

    @Override
    public void onSuccess(ArgusUser user, ResultState state) {
        listener.onSuccess(user, state);
    }

    @Override
    public void onFailure(String message, ResultState state) {
        listener.onFailure(message, state);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ResultListener) {
            listener = (ResultListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ResultListener");
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

    protected abstract int getLayoutId();

    protected abstract List<BaseProvider> getProviders();
}
