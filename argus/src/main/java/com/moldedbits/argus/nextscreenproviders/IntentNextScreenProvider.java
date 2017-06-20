package com.moldedbits.argus.nextscreenproviders;

import android.content.Context;
import android.content.Intent;

/**
 * Created by abhishek
 * on 20/06/17.
 */

public class IntentNextScreenProvider implements NextScreenProvider {
    private Intent intent;

    public IntentNextScreenProvider(Intent intent) {
        this.intent = intent;
    }

    @Override
    public Intent getNextScreen(Context context) {
        return intent;
    }
}
