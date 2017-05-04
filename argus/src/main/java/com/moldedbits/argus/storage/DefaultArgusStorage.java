package com.moldedbits.argus.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.moldedbits.argus.model.ArgusUser;


public class DefaultArgusStorage implements ArgusStorage {
    private static final String ARGUS_PREFERENCES_STORE = "com.moldedbits.argus.sharedprefs";
    private static final String ARGUS_USER = "com.moldedbits.argus.argususer";
    private SharedPreferences mSharedPreferences = null;

    private Editor mSharedPreferencesEditor = null;

    public void clearPrefs() {
        mSharedPreferencesEditor.clear().commit();
    }

    @SuppressLint("CommitPrefEdits")
    public DefaultArgusStorage(@NonNull final Context context) {
        if(context == null) {
            throw new IllegalArgumentException("Context provided to storage is null");
        }
        mSharedPreferences = context.getSharedPreferences(ARGUS_PREFERENCES_STORE, 0); // 0 - for private mode
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public void setCurrentUser(ArgusUser user) {
        mSharedPreferencesEditor.putString(ARGUS_USER, new Gson().toJson(user)).apply();
    }

    public ArgusUser getCurrentUser() {
        String userString = mSharedPreferences.getString(ARGUS_USER, "");
        return new Gson().fromJson(userString, ArgusUser.class);
    }
}