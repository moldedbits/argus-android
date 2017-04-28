package com.moldedbits.argus.provider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.moldedbits.argus.R;
import com.moldedbits.argus.model.ArgusUser;

import org.json.JSONObject;

import java.util.Arrays;

public class FaceBookLoginProvider extends LoginProvider {

    // Permissions can be changed

    public static final String[] FACEBOOK_APP_PERMISSIONS =
            {
                    "public_profile",
                    "email",
                    "user_photos",
            };

    private static CallbackManager callbackManager;

    public FaceBookLoginProvider() {
        callbackManager = CallbackManager.Factory.create();
        setUpCallBack();
    }

    private void setUpCallBack() {
        LoginManager.getInstance()
                .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {

                        GraphRequest graphRequest = GraphRequest
                                .newMeRequest(loginResult.getAccessToken()
                                        , new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object,
                                                                    GraphResponse response) {
                                               onLoginSuccess(new ArgusUser("Hello"+ AccessToken
                                                       .getCurrentAccessToken().getUserId()));
                                            }
                                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name,email,public_profile");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("FACEBOOK", error.getMessage());
                    }
                });
    }

    @Override
    protected View inflateLoginView(ViewGroup parentView) {
        return LayoutInflater.from(context)
                .inflate(R.layout.facebook_login, parentView, false);
    }

    @Override
    void performLogin() {
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList(
                FACEBOOK_APP_PERMISSIONS));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
