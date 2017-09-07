package com.moldedbits.argus.provider.social.helper;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.List;

public class FacebookHelper {

    private List<String> facebookPermissions;

    public interface FBLoginResultListener {
        void onSuccess(AccessToken token);

        void onFailure(String message);
    }

    private AccessToken token;
    private CallbackManager callbackManager;
    private FBLoginResultListener resultListener;

    public FacebookHelper(FBLoginResultListener resultListener) {
        this.resultListener = resultListener;
        callbackManager = CallbackManager.Factory.create();
    }

    public void initialize() {
        LoginManager.getInstance()
                .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest graphRequest = GraphRequest
                                .newMeRequest(loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object,
                                                                    GraphResponse response) {
                                                token = AccessToken.getCurrentAccessToken();
                                                if (resultListener != null) {
                                                    resultListener.onSuccess(token);
                                                }
                                            }
                                        });
                        Bundle parameters = new Bundle();
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        resultListener.onFailure(error.getMessage());
                    }
                });
    }


    public void logout() {
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void initiateLogin(Fragment fragment, List<String> faceBookPermissions) {
        this.facebookPermissions = faceBookPermissions;
        LoginManager.getInstance()
                .logInWithReadPermissions(fragment, faceBookPermissions);
    }
}

