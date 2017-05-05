package com.moldedbits.argus.provider.social;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.R;
import com.moldedbits.argus.helper.GoogleHelper;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;


public class GoogleOnBoardingProvider extends BaseProvider implements LoginListener {
    private GoogleHelper googleHelper;

    @Override
    protected void performLogin() {
        googleHelper = new GoogleHelper(fragment, this);
        googleHelper.initializeGoogleApiClient();
        googleHelper.onSignInClicked();
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        return LayoutInflater.from(context).inflate(R.layout.google_signup, parentView, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleHelper.RC_SIGN_IN) {
            googleHelper.onActivityResult(requestCode, resultCode, data);
        }
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
    public int getContainerId() {
        return R.id.container_google;
    }
}
