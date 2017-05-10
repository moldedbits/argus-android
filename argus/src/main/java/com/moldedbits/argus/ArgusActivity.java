package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.model.ArgusUser;

public class ArgusActivity extends AppCompatActivity
        implements ResultListener {

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
    public void onSuccess(ArgusUser user, ResultState resultState) {
        Argus.getInstance().loginUser(user);
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    @Override
    public void onFailure(String message, ResultState resultState) {
        //TODO handle failure correctly
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void onSignUpClicked() {

    }
}
