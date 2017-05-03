package com.moldedbits.argus.provider.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.moldedbits.argus.R;
import com.moldedbits.argus.helper.FaceBookConfig;
import com.moldedbits.argus.helper.FaceBookHelper;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;

public class FaceBookLoginProvider extends LoginProvider implements LoginListener {

    // Permissions can be changed

    private FaceBookHelper faceBookHelper;

    public FaceBookLoginProvider() {
        faceBookHelper = new FaceBookHelper(this);
        faceBookHelper.setupFacebookCallback();
    }

    @Override
    protected void performLogin() {
        LoginManager.getInstance()
                .logInWithReadPermissions(fragment, new FaceBookConfig().getFaceBookPermissions());
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        return LayoutInflater.from(context)
                .inflate(R.layout.facebook_login, parentView, false);
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
        faceBookHelper.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getContainerId() {
        return R.id.container_fb;
    }
}
