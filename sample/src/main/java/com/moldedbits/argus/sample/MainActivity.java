package com.moldedbits.argus.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.model.ArgusUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArgusUser currentUser = Argus.getInstance().getCurrentUser();
        if (currentUser != null) {
            ((TextView) findViewById(R.id.username)).setText(currentUser.getUsername());
        }
    }
}
