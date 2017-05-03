package com.moldedbits.argus.provider.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.moldedbits.argus.R;
import com.moldedbits.argus.helper.GoogleHelper;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;

public class GoogleLoginProvider extends LoginProvider implements LoginListener {

    private GoogleHelper googleHelper;

    /**
     * Inflate your login view here
     *
     * @param parentView Parent view
     * @return Inflated view
     */
    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        return LayoutInflater.from(context).inflate(R.layout.login_google, parentView, false);
    }

    /**
     * Perform login here. Implementations should take care of showing loading overlay to block
     * out UI
     */
    @Override
    void performLogin() {
        googleHelper = new GoogleHelper(fragment, this);
        googleHelper.initializeGoogleApiClient();
        googleHelper.onSignInClicked();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleHelper.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleHelper.handleSignInResult(result);
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
