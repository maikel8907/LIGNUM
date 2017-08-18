package com.bachduong.bitwallet.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ServicePrefs {
    private static final String TAG = ServicePrefs.class.getSimpleName();
    private static final String KEY_PASSWORD_DEVICE = "jkh6";
    private static final String KEY_PASSWORD_WALLET = "dfsf";
    private static final String KEY_DEVICE_NAME = "name";

    private String devicePassword;
    private String walletPassword;
    private String deviceName;

    private SharedPreferences sharedPrefs;

    public ServicePrefs(Context context) {
        if (context == null) {
            Log.d(TAG, "Prefs should not be initialized with a null context!");
            return;
        }

        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.devicePassword = getString(KEY_PASSWORD_DEVICE);
        this.walletPassword = getString(KEY_PASSWORD_WALLET);
        this.deviceName = getString(KEY_DEVICE_NAME);

    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        saveString(KEY_DEVICE_NAME, deviceName);
    }

    public String getDevicePassword() {
        return devicePassword;
    }

    public String getWalletPassword() {
        return walletPassword;
    }

    public void setDevicePassword(String devicePassword) {
        this.devicePassword = devicePassword;
        saveString(KEY_PASSWORD_DEVICE, devicePassword);
    }

    public void setWalletPassword(String walletPassword) {
        this.walletPassword = walletPassword;
        saveString(KEY_PASSWORD_WALLET, walletPassword);
    }

    public void saveBoolean(String key, Boolean value) {
        this.sharedPrefs.edit().putBoolean(key, value).commit();
    }

    public Boolean getBoolean(String key) {
        return this.sharedPrefs.getBoolean(key, false);
    }

    public void saveString(String key, String value) {
        this.sharedPrefs.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return this.sharedPrefs.getString(key, "");
    }


}