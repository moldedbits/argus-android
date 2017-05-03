package com.moldedbits.argus.provider.signup;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.moldedbits.argus.R;
import com.moldedbits.argus.helper.FaceBookHelper;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;

import java.util.Arrays;

import static com.moldedbits.argus.provider.login.FaceBookLoginProvider.FACEBOOK_APP_PERMISSIONS;


public class FaceBookSignupProvider extends SignupProvider implements LoginListener {

    private FaceBookHelper faceBookHelper;

    public FaceBookSignupProvider() {
        faceBookHelper = new FaceBookHelper(this);
        faceBookHelper.setupFacebookCallback();
    }

    @Override
    protected void performSignUp() {
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList(
                FACEBOOK_APP_PERMISSIONS));

    }

    @Override
    protected View inflateSignUpView(ViewGroup parentView) {
        return LayoutInflater.from(context)
                .inflate(R.layout.facebook_signup, parentView, false);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        faceBookHelper.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }
}
