package com.bachduong.bitwallet.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ServicePrefs {
    private static final String TAG = ServicePrefs.class.getSimpleName();
    private static final String KEY_PASSWORD = "jkh6";
    private String password;

    private SharedPreferences sharedPrefs;

    public ServicePrefs(Context context) {
        if (context == null) {
            Log.d(TAG, "Prefs should not be initialized with a null context!");
            return;
        }

        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.password = getString(KEY_PASSWORD);
    }

    public String getPassword() {
        this.password = getString(KEY_PASSWORD);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        saveString(KEY_PASSWORD, password);
    }

    public void saveBoolean(String key, Boolean value) {
        this.sharedPrefs.edit().putBoolean(key,value).commit();
    }

    public Boolean getBoolean(String key) {
        return this.sharedPrefs.getBoolean(key, false);
    }

    public void saveString(String key, String value) {
        this.sharedPrefs.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return this.sharedPrefs.getString(key,"");
    }


}