package com.moldedbits.argus.provider.social;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.moldedbits.argus.ArgusState;
import com.moldedbits.argus.R;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.social.helper.FacebookConfig;
import com.moldedbits.argus.provider.social.helper.FacebookHelper;


public class FacebookOnBoardingProvider extends BaseProvider
        implements FacebookHelper.FBLoginResultListener {

    private FacebookHelper facebookHelper;

    public FacebookOnBoardingProvider() {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getContainerId() {
        return R.id.container_social;
    }

    @Override
    public void onSuccess(AccessToken token) {
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
}
