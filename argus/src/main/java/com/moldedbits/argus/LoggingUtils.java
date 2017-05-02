package com.moldedbits.argus;

import android.util.Log;

public class LoggingUtils {

    private static final String TAG = "Argus";

    public static void d(String message) {
        if (areLogsEnabled()) {
            Log.d(TAG, message);
        }
    }

    public static void printStackTrace(Throwable e) {
        if (areLogsEnabled()) {
            e.printStackTrace();
        }
    }

    private static boolean areLogsEnabled() {
        return Argus.getInstance().getConfig().logEnabled;
    }
}
