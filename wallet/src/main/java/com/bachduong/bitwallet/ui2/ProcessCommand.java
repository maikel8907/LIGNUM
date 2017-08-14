package com.bachduong.bitwallet.ui2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bachduong.bitwallet.Constants;
import com.bachduong.bitwallet.service.Server;
import com.bachduong.core.coins.BitcoinMain;
import com.bachduong.core.wallet.WalletAccount;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private int currentStep = 0;
    private String devicePin = "";
    private String walletPin = "";
    private String deviceName = "";
    public int chooseMode = 0;

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
                Log.d(LOG_TAG, "Command received:" + dataCommand.getCommand());
//                Log.d(LOG_TAG, "Command data:" + dataCommand.getData());

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
//                this.seeds = null;
//                this.isSeedGenerated = false;
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
                Log.d(LOG_TAG, "show-finish called");
                this.deviceName = "test";
                this.devicePin = "1111";
                this.walletPin = "1111";
                activity.showFinishFragment();
                activity.createWallet(seeds, walletPin, devicePin);
                callback.onResponse(response.toJson());
                this.seeds = null;
                this.isSeedGenerated = false;
                return;

            case "get-device-status":
                response.status = true;
                callback.onResponse(response.toJson());
                return;

            case "get-device-data":
                try {
                    response.status = true;
                    Map<String, Object> dataDevice = new HashMap<>();
                    dataDevice.put("currentStep", currentStep);
                    dataDevice.put("devicePin", devicePin);
                    dataDevice.put("walletPin", walletPin);
                    dataDevice.put("recoveryWordList", getSeeds());
                    dataDevice.put("deviceName", deviceName);
                    response.data = dataDevice;
                    callback.onResponse(response.toJson());
                } catch (Exception e) {
                    response.status = false;
                    response.data = e.getMessage();
                    e.printStackTrace();
                    callback.onResponse(response.toJson());
                }
                return;


            case "get-wallet":
                if (activity.getWalletApplication().getWallet() != null) {
                    try {
                        response.status = true;
                        List<WalletAccount> accounts = activity.getWalletApplication().getWallet().getAccounts(Constants.SUPPORTED_COINS);
                        response.data = convertToResponeWallet(accounts);
                        callback.onResponse(response.toJson());
                    } catch (Exception e) {
                        response.status = false;
                        response.data = e.getMessage();
                        e.printStackTrace();
                        callback.onResponse(response.toJson());
                    }
                } else {
                    response.status = false;
                    callback.onResponse(response.toJson());
                }
                return;

            case "get-mode":
                if (chooseMode > 0) {
                    response.status = true;
                    Map<String, Object> chooseMode = new HashMap<>();
                    chooseMode.put("mode", this.chooseMode);
                    response.data = chooseMode;
                } else {
                    response.status =false;
                }
                callback.onResponse(response.toJson());
                return;
            default:
                response.status = false;
                response.data = "Nothing here";
                callback.onResponse(response.toJson());
                return;
        }
    }

    private List<ResponseWallet> convertToResponeWallet(List<WalletAccount> accounts) {
        List<ResponseWallet> responseWallets = new ArrayList<>();
        for (WalletAccount account : accounts) {
            ResponseWallet wallet = new ResponseWallet();
            wallet.address = account.getReceiveAddress().toString();
            wallet.type = account.getCoinType().getSymbol();
            wallet.value = account.getBalance().toString();
            wallet.name = account.getDescriptionOrCoinName();
            responseWallets.add(wallet);
        }
        return responseWallets;
    }

    private Map<Integer, String> getSeeds() {
        if (seeds == null || seeds.length ==0 ) {
            return new HashMap<>();
        }
        Map<Integer, String> map = new HashMap<>();
        int size = seeds.length;
        for (int i = 0; i < size; i++) {
            map.put(i+1, seeds[i]);
        }
        Map<Integer, String> treeMap = new TreeMap<Integer, String>(map);
        return treeMap;
    }

    public void setSeeds(String[] seeds) {
        this.seeds = seeds;
    }

    private HashMap<String, Object> getDeviceData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("devicePin", devicePin);
        data.put("walletPin", walletPin);
        data.put("recoveryWordList", getSeeds());
        data.put("deviceName", deviceName);
        return data;
    }

    public class DataResponse {
        public boolean status = true;
        public Object data;

        public String toJson() {
            return gson.toJson(this);
        }
    }

    public class ResponseWallet {
        public Object type;
        public String name;
        public String address;
        public Object value;
    }
}
