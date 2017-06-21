package com.moldedbits.argus.nextscreenproviders;

import android.content.Context;
import android.content.Intent;

/**
 * Provide the first screen to launch after logging in
 */

public interface NextScreenProvider {

    Intent getNextScreen(Context context);
}
