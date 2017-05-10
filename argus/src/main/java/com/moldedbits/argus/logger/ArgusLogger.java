package com.moldedbits.argus.logger;

import android.util.Log;

/**
 * Created by abhishek
 * on 10/05/17.
 */

// TODO: 10/05/17 define log levels and log as per current log level
public final class ArgusLogger {
    // no need to create an object
    private ArgusLogger(){}

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void w(String tag, String message) {
        Log.w(tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }
}
