package com.moldedbits.argus.provider.social;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.moldedbits.argus.ArgusState;
import com.moldedbits.argus.R;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.social.helper.GoogleHelper;

import lombok.Getter;


public class GoogleOnBoardingProvider extends BaseProvider
        implements GoogleHelper.GoogleLoginResultListener {

    private GoogleHelper googleHelper;
    @Getter
    private String serverClientId;

    @Override
    protected void performAction() {
        googleHelper = new GoogleHelper(fragment, this);
        googleHelper.initializeGoogleApiClient(getServerClientId());
        googleHelper.onSignInClicked();
    }

    @Override
    protected View inflateView(ViewGroup parentView) {
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
            resultListener.onSuccess(ArgusState.SIGNED_IN);
        }
    }

    @Override
    public void onFailure(String message) {
        if (resultListener != null) {
            resultListener.onFailure(message);
        }
    }

    public void setServerClientId(String serverClientId) {
        this.serverClientId = serverClientId;
    }
}
