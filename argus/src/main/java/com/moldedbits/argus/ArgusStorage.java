package com.moldedbits.argus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.moldedbits.argus.model.ArgusUser;


class ArgusStorage {
    private static final String HUE_SHARED_PREFERENCES_STORE = "HueSharedPrefs";
    private static final String ARGUS_USER = "com.moldedbits.argus.argususer";
    private static ArgusStorage instance = null;
    private SharedPreferences mSharedPreferences = null;

    private Editor mSharedPreferencesEditor = null;

    public void clearPrefs() {
        mSharedPreferencesEditor.clear().commit();
    }

    @SuppressLint("CommitPrefEdits")
    ArgusStorage(@NonNull final Context context) {
        mSharedPreferences = context.getSharedPreferences(HUE_SHARED_PREFERENCES_STORE, 0); // 0 - for private mode
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    void setCurrentUser(ArgusUser user) {
        mSharedPreferencesEditor.putString(ARGUS_USER, new Gson().toJson(user));
    }

    ArgusUser getCurrentUser() {
        String userString = mSharedPreferences.getString(ARGUS_USER, "");
        return new Gson().fromJson(userString, ArgusUser.class);
    }
}
