package com.moldedbits.argus.nextscreenproviders;

import android.content.Context;
import android.content.Intent;

public class SimpleNextScreenProvider implements NextScreenProvider {

    private Class nextScreen;

    public SimpleNextScreenProvider(Class nextScreen) {
        this.nextScreen = nextScreen;
    }

    @Override
    public Intent getNextScreen(Context context) {
        return new Intent(context, nextScreen);
    }
}
