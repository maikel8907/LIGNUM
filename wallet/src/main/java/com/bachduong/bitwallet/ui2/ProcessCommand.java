package com.bachduong.bitwallet.ui2;

import android.os.Bundle;
import android.util.Log;

import com.bachduong.bitwallet.service.Server;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by duongtung on 7/31/17.
 */

public class ProcessCommand implements Server.TransporterListener {
    private static final String LOG_TAG = ProcessCommand.class.getSimpleName();
    public boolean isSeedGenerated = false;
    Pattern mPattern = Pattern.compile("content-custom: (.*)");
    private MainActivity activity;
    private Gson gson = new Gson();
    private Map<Integer, String> currentKeyMap;
    private String[] seeds;

    public ProcessCommand(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceived(String receive, Server.TransporterListener callback) {
        try {
            String data;
            Matcher matcher = mPattern.matcher(receive);
            if (matcher.find()) {
                data = matcher.group(1);
                DataCommand dataCommand = gson.fromJson(data, DataCommand.class);
                Log.d(LOG_TAG, "get data:" + dataCommand.getCommand());

                process(dataCommand, callback);
            } else {

                DataCommand dataCommand = new DataCommand();
                process(dataCommand, callback);

            }
        } catch (Exception e) {
            DataResponse response = new DataResponse();
            response.status = false;
            response.data = (new HashMap<>()).put("error", e.getMessage());
            callback.onResponse(response.toJson());
        }
    }

    @Override
    public void onResponse(String response) {

    }

    public void setCurrentKeyMap(Map<Integer, String> currentKeyMap) {
        this.currentKeyMap = currentKeyMap;
    }

    private void process(DataCommand dataCommand, final Server.TransporterListener callback) {
        final DataResponse response = new DataResponse();
        if (dataCommand == null || dataCommand.getCommand() == null) {
            response.status = false;
            response.data = "Invalid command format";
            //return gson.toJson(response);
            callback.onResponse(response.toJson());
            return;
        }
        switch (dataCommand.getCommand()) {

            case "check-is-setup":
                Map<String, Boolean> map = new HashMap<>();
                map.put("is-setup", false);
                response.data = map;
                activity.showChooseModeFragment();
                callback.onResponse(response.toJson());
                return;
            case "set-device-name":

                callback.onResponse(response.toJson());
                return;
            case "get-keyboard-map":
                activity.showPinFragment(new PinLoginFragment.Listener() {
                    @Override
                    public void onPasswordSetStep1(String password) {

                    }

                    @Override
                    public void onPasswordSetFinal(String password) {

                    }

                    @Override
                    public void onLoginSuccess(Bundle agrs) {

                    }

                    @Override
                    public void onFinishLoadKeyMap(Map<Integer, String> keyMap) {
                        if (keyMap != null) {
                            response.data = keyMap;
                        } else {
                            response.status = false;
                        }
                        callback.onResponse(response.toJson());
                    }
                });
                return;
            case "show-status":
                HashMap<String, String> data;
                try {
                    data = dataCommand.getData();
                    if (data != null) {
                        String title = data.get("title");
                        String content = data.get("content");
                        activity.showStatusFragment(title, content);
                    } else {
                        response.status = false;
                    }
                } catch (Exception e) {
                    response.status = false;
                    response.data = (new HashMap<>()).put("error", e.getMessage());
                    callback.onResponse(response.toJson());
                } finally {

                    callback.onResponse(response.toJson());
                }

                return;
            case "set-pin":

                callback.onResponse(response.toJson());
                return;
            case "check-recovery-phase-1":
                activity.showStatusFragment("Configuring Device", "Confirmation");
                callback.onResponse(response.toJson());
                return;
            case "check-recovery-phase-2":
                activity.showStatusFragment("Confirmation", "Confirm in your computer the order of the words as you wrote on the seed");
                callback.onResponse(response.toJson());
                return;
            case "check-recovery-phase-finish":
                activity.showLoadingFragment(true);
                callback.onResponse(response.toJson());
                return;
            case "get-recovery-phase":
                if (!isSeedGenerated) {
                    activity.showSeedFragment();
                }
                if (seeds != null && seeds.length > 0) {
                    response.data = getSeeds();
                } else {
                    response.status = false;
                }
                callback.onResponse(response.toJson());
                return;
            case "show-finish":
                activity.showFinishFragment();
                callback.onResponse(response.toJson());
                return;

            default:
                response.status = false;
                response.data = "Nothing here";
                callback.onResponse(response.toJson());
                return;
        }
    }

    private Map<Integer, String> getSeeds() {
        Map<Integer, String> map = new HashMap<>();
        int size = seeds.length;
        for (int i = 0; i < size; i++) {
            map.put(i, seeds[i]);
        }
        Map<Integer, String> treeMap = new TreeMap<Integer, String>(map);
        return treeMap;
    }

    public void setSeeds(String[] seeds) {
        this.seeds = seeds;
    }

    public class DataResponse {
        public boolean status = true;
        public Object data;

        public String toJson() {
            return gson.toJson(this);
        }
    }
}
