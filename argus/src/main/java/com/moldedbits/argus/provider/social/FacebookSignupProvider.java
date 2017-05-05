package com.moldedbits.argus.provider.social;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.R;
import com.moldedbits.argus.helper.FacebookConfig;
import com.moldedbits.argus.helper.FacebookHelper;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;


public class FacebookSignupProvider extends BaseProvider implements LoginListener {

    private FacebookHelper facebookHelper;

    public FacebookSignupProvider() {
        facebookHelper = new FacebookHelper(this);
        facebookHelper.initialize();
    }

    @Override
    protected void performLogin() {
        facebookHelper.initiateLogin(fragment, new FacebookConfig().getFaceBookPermissions());
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        return LayoutInflater.from(context)
                .inflate(R.layout.facebook_signup, parentView, false);
    }

    @Override
    public void onSuccess(ArgusUser user) {
        onLoginSuccess(user);
    }

    @Override
    public void onFailure(String message) {
        onLoginFail(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getContainerId() {
        return R.id.container_fb;
    }
}
