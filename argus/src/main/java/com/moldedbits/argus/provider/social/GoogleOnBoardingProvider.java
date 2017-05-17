package com.moldedbits.argus.provider.social;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.moldedbits.argus.R;
import com.moldedbits.argus.State;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.social.helper.GoogleHelper;


public class GoogleOnBoardingProvider extends BaseProvider
        implements GoogleHelper.GoogleLoginResultListener {
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
    public int getContainerId() {
        return R.id.container_social;
    }

    @Override
    public void onSuccess(GoogleSignInAccount account) {
        if (resultListener != null) {
            resultListener.onSuccess(State.SIGNED_IN);
        }
    }

    @Override
    public void onFailure(String message) {
        if (resultListener != null) {
            resultListener.onFailure(message);
        }
    }
}
