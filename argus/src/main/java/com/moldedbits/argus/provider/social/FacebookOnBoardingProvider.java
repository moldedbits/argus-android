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

import java.util.List;

public class FacebookOnBoardingProvider extends BaseProvider
        implements FacebookHelper.FBLoginResultListener {

    private FacebookHelper facebookHelper;

    /**
     * This is used to set permissions for facebook token and if no permissions are set then default
     * "public_profile" will be used to generate the token.
     */
    private FacebookConfig facebookConfig;

    public FacebookOnBoardingProvider() {
        facebookHelper = new FacebookHelper(this);
        facebookHelper.initialize();
        facebookConfig = new FacebookConfig();
    }

    @Override
    protected void performAction() {
        List<String> permissionList = facebookConfig.getFaceBookPermissions();
        if (permissionList == null || permissionList.size() == 0) {
            facebookConfig.getFaceBookPermissions().add(FacebookConfig.PUBLIC_PROFILE);
            facebookHelper.initiateLogin(fragment, facebookConfig.getFaceBookPermissions());
        } else {
            facebookHelper.initiateLogin(fragment, permissionList);
        }
    }

    @Override
    protected View inflateView(ViewGroup parentView) {
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

    public void setFacebookPermission(List<String> permissionList) {
        facebookConfig.setFaceBookPermissions(permissionList);
    }
}
