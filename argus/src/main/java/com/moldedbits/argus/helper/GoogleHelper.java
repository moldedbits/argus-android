package com.moldedbits.argus.helper;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.model.ArgusUser;

public class GoogleHelper implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int RC_SIGN_IN = 10001;
    private Fragment fragment;
    private LoginListener listener;
    private GoogleApiClient googleApiClient = null;
    private boolean isResolving;
    private boolean shouldResolve;
    private Boolean mGooglePlusLogoutClicked;

    public GoogleHelper(Fragment fragment, LoginListener listener) {
        this.fragment = fragment;
        this.listener = listener;
    }

    public void initializeGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(fragment.getContext())
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

    //TODO create a confif file for printing logs from classes depending on Log level.
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("CONNECT_FAILED", "onConnectionFailed:" + connectionResult);

        if (!isResolving && shouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(fragment.getActivity(), RC_SIGN_IN);
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

    private void connectClient() {
        googleApiClient.connect();
    }

    public void onSignInClicked() {
        shouldResolve = true;
        connectClient();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void logOut() {
        mGooglePlusLogoutClicked = true;
        if (googleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(googleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            //TODO handle logic for onlogout
                        }
                    });
        } else {
            googleApiClient.connect();
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                listener.onSuccess(new ArgusUser(acct.getDisplayName()));
            }
        } else {
            // Signed out, show unauthenticated UI.
            // TODO Show correct message
            listener.onFailure("Error");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        handleSignInResult(result);
    }
}