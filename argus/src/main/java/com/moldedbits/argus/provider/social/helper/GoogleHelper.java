package com.moldedbits.argus.provider.social.helper;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class GoogleHelper implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public interface GoogleLoginResultListener {
        void onSuccess(GoogleSignInAccount account);

        void onFailure(String message);
    }

    public static final int RC_SIGN_IN = 10001;
    private Fragment fragment;
    private GoogleLoginResultListener listener;
    private GoogleApiClient googleApiClient = null;
    private boolean isResolving;
    private boolean shouldResolve;
    private Boolean mGooglePlusLogoutClicked;

    public GoogleHelper(Fragment fragment, GoogleLoginResultListener listener) {
        this.fragment = fragment;
        this.listener = listener;
    }

    /**
     * Initializing google api client when client id provided for token verification on a custom backend
     *
     * @param serverClientId It is a Web Client Id autocreated on
     * @see <a href="https://console.developers.google.com/">Google developer console</a> on
     * creating project configuration.
     */
    public void initializeGoogleApiClient(String serverClientId) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestIdToken(serverClientId)
                .build();
        googleApiClient = new GoogleApiClient.Builder(fragment.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * Initializing google api client if no client id provided
     * It will throw null pointer exception while requesting Id token from GoogleSignInAccount
     */
    public void initializeGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (!isResolving && shouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(fragment.getActivity(), RC_SIGN_IN);
                    isResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    isResolving = false;
                    googleApiClient.connect();
                }
            } else {
                showErrorDialog(connectionResult);
            }
        }
    }

    private void showErrorDialog(ConnectionResult connectionResult) {
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

    private void logOut() {
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

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                listener.onSuccess(acct);
            } else {
                listener.onFailure("login failed");
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