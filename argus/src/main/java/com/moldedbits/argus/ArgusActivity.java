package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.LoginProvider;

public class ArgusActivity extends AppCompatActivity implements
        LoginProvider.LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argus);

        if (Argus.getInstance() == null) {
            throw new RuntimeException("Argus not initialized");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, LoginFragment.newInstance())
                .commit();
    }

    @Override
    public void onLoginSuccess(ArgusUser user) {
        Argus.getInstance().loginUser(user);

        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    @Override
    public void onLoginFailure() {
        // TODO
    }
}
