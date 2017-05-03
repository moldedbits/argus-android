package com.moldedbits.argus.provider.signup;

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

/**
 * Created by shishank on 03/05/17.
 */

public class GoogleSignupProvider extends SignupProvider implements LoginListener {


    private GoogleHelper googleHelper;

    @Override
    protected void performSignUp() {
        googleHelper = new GoogleHelper(fragment, this);
        googleHelper.initializeGoogleApiClient();
        googleHelper.onSignInClicked();
    }

    @Override
    protected View inflateSignUpView(ViewGroup parentView) {
        return LayoutInflater.from(context).inflate(R.layout.google_signup, parentView, false);
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
        onSignupSuccess(user);
    }

    @Override
    public void onFailure(String message) {
        onSignupFailure(message);
    }

    @Override
    public int getContainerId() {
        return R.id.container_google;
    }
}
