package com.moldedbits.argus.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.moldedbits.argus.listener.LoginListener;

public class GoogleHelper implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {
    private Context context;
    private LoginListener listener;
    private GoogleApiClient googleApiClient = null;
    boolean isResolving;
    boolean shouldResolve;
    private Boolean mGooglePlusLogoutClicked;


    public GoogleHelper(Fragment fragment, LoginListener listener) {
        context = fragment.getContext();
        this.listener = listener;
    }

    public void initializeGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mGooglePlusLogoutClicked != null && mGooglePlusLogoutClicked) {
            logOut();
            mGooglePlusLogoutClicked = false;
        }
        shouldResolve = false;
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("CONNECT_FAILED", "onConnectionFailed:" + connectionResult);

        if (!isResolving && shouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult((Activity) context, 9002);
                    isResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e("TAG", "Could not resolve ConnectionResult.", e);
                    isResolving = false;
                    googleApiClient.connect();
                }
            } else {
                showErrorDialog(connectionResult);
            }
        }
    }

    private void showErrorDialog(ConnectionResult connectionResult) {
        Log.d("ERROR", "" + connectionResult);
    }

    public void connectClient() {
        googleApiClient.connect();
    }

    public void disconnectClient() {
        googleApiClient.disconnect();
    }

    public void onSignInClicked() {
        shouldResolve = true;
        connectClient();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        ((AppCompatActivity) context).startActivityForResult(signInIntent, 9002);
    }

    public void logOut() {
        mGooglePlusLogoutClicked = true;
        if (googleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(googleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            //AppUtils.takeToLoginActivity(context);
                        }
                    });
        } else {
            googleApiClient.connect();
        }
    }
}