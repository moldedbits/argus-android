package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;

public class ArgusActivity extends AppCompatActivity
        implements ResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argus);

        if (Argus.getInstance() == null) {
            throw new RuntimeException("Argus not initialized");
        }

        setupView();
    }

    private void setupView() {
        switch (Argus.getInstance().getState()) {
            case SIGNED_OUT:
                showLoginFragment();
                break;
            case IN_PROGRESS:
                showProgressView();
                break;
            case SIGNED_IN:
                showNextScreen();
                break;
        }
    }

    private void showProgressView() {
        BaseProvider provider = Argus.getInstance().getProviderInProgress();
        Fragment progressView = provider == null ? null : provider.getProgressView();
        if (progressView == null) {
            showLoginFragment();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, progressView)
                    .commit();
        }
    }

    private void showLoginFragment() {
        BaseFragment f = SignupFragment.newInstance();
        f.setListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, f)
                .commit();
    }

    @Override
    public void onSuccess(ArgusUser user, ArgusState resultState) {
        Argus.getInstance().setState(resultState);
        if (resultState == ArgusState.SIGNED_IN) {
            Argus.getInstance().loginUser(user);
            showNextScreen();
        } else {
            setupView();
        }
    }

    @Override
    public void onFailure(String message, ArgusState resultState) {
        //TODO handle failure correctly
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showNextScreen() {
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    private void onSignUpClicked() {

    }
}
