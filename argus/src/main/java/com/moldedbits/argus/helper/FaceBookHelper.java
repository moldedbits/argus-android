package com.moldedbits.argus.helper;


import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;

import org.json.JSONObject;


public class FaceBookHelper {
    private AccessToken token;
    private static CallbackManager callbackManager;
    private LoginListener loginListener;

    public FaceBookHelper(LoginListener loginListener) {
        this.loginListener = loginListener;
        callbackManager = CallbackManager.Factory.create();
    }

    public void setupFacebookCallback() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken()
                        , new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("FACEBOOK","asdsadsad");
                                token = AccessToken.getCurrentAccessToken();
                                if (loginListener != null) {
                                    loginListener.onSuccess(new ArgusUser(token.toString()));
                                }
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
                loginListener.onFailure(error.getMessage());
                Log.d("FACEBOOK",error.getMessage());
            }
        });
    }


    public void logoutFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return;
            // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}

