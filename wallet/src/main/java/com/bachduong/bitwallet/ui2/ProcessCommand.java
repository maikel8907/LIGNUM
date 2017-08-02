package com.bachduong.bitwallet.ui2;

import android.os.Handler;
import android.util.Log;

import com.bachduong.bitwallet.service.Server;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;

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

    private MainActivity activity;
    private Gson gson = new Gson();
    private Map<Integer, String> currentKeyMap;
    private String[] seeds;
    public boolean isSeedGenerated = false;

    Pattern mPattern = Pattern.compile("content-custom: (.*)");

    public ProcessCommand(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceived(String receive, Server.TransporterListener callback) {

        String data;
        Matcher matcher = mPattern.matcher(receive);
        if(matcher.find())
        {
            data = matcher.group(1); // dont know what to place
            DataCommand dataCommand = gson.fromJson(data, DataCommand.class);
            Log.d(LOG_TAG, "get data:" + dataCommand.getCommand());
            callback.onResponse(process(dataCommand));
        } else {
            //receive = receive + "\r\nPing back after received \n";
//            String response =
//                    "<p>\n" +
//                            "{status : true; data : {})" +
//                            "</p>\n";
//            callback.onResponse(response);
            DataCommand dataCommand = new DataCommand();
            callback.onResponse(process(dataCommand));
        }
    }

    @Override
    public void onResponse(String response) {

    }

    public void setCurrentKeyMap(Map<Integer, String> currentKeyMap) {
        this.currentKeyMap = currentKeyMap;
    }

    private String process(DataCommand dataCommand) {
        DataResponse response = new DataResponse();
        if (dataCommand == null || dataCommand.getCommand() == null) {
            response.status = false;
            response.data = "Invalid command format";
            return gson.toJson(response);
        }
        switch (dataCommand.getCommand()) {

            case "check-is-setup":
                Map<String, Boolean> map = new HashMap<>();
                map.put("is-setup", false);
                response.data = map;
                activity.showChooseModeFragment();
                break;
            case "set-device-name":

                break;
            case "get-keyboard-map":
                activity.showPinFragment();
                if (currentKeyMap != null) {
                    response.data = currentKeyMap;
                } else {
                    response.status = false;
                }
                break;
            case "set-pin":

                break;
            case "check-recovery-phase-1":
                activity.showStatusFragment("Configuring Device", "Confirmation");
                break;
            case "check-recovery-phase-2":
                activity.showStatusFragment("Confirmation", "Confirm in your computer the order of the words as you wrote on the seed");
                break;
            case "check-recovery-phase-finish":
                activity.showLoadingFragment(true);
                break;
            case "get-recovery-phase":
                if (!isSeedGenerated) {
                    activity.showSeedFragment();
                }
                if (seeds != null && seeds.length > 0) {
                    response.data = getSeeds();
                } else {
                    response.status = false;
                }

                break;

            default:
                response.status = false;
                response.data = "Nothing here";

        }

        return gson.toJson(response);
    }

    public void setSeeds(String[] seeds) {
        this.seeds = seeds;
    }

    private Map<Integer, String> getSeeds() {
        Map<Integer, String> map = new HashMap<>();
        int size = seeds.length;
        for (int i = 0; i< size; i++) {
            map.put(i, seeds[i]);
        }
        Map<Integer, String> treeMap = new TreeMap<Integer, String>(map);
        return treeMap;
    }


    public class DataResponse {
        public boolean status = true;
        public Object data;
    }
}
