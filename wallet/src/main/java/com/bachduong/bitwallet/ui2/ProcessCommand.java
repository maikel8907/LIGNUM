package com.bachduong.bitwallet.ui2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bachduong.bitwallet.Constants;
import com.bachduong.bitwallet.WalletApplication;
import com.bachduong.bitwallet.service.Server;
import com.bachduong.bitwallet.service.ServicePrefs;
import com.bachduong.core.coins.BitcoinMain;
import com.bachduong.core.coins.BitcoinTest;
import com.bachduong.core.coins.CoinType;
import com.bachduong.core.coins.Value;
import com.bachduong.core.coins.ValueType;
import com.bachduong.core.coins.families.NxtFamily;
import com.bachduong.core.exceptions.AddressMalformedException;
import com.bachduong.core.util.GenericUtils;
import com.bachduong.core.util.MonetaryFormat;
import com.bachduong.core.wallet.AbstractAddress;
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

    private ServicePrefs servicePrefs;
    private MonetaryFormat format = new MonetaryFormat().noCode();

    public ProcessCommand(MainActivity activity) {
        this.activity = activity;
        servicePrefs = new ServicePrefs(activity);

        devicePin = servicePrefs.getDevicePassword();
        walletPin = servicePrefs.getWalletPassword();
        deviceName = servicePrefs.getDeviceName();
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
                HashMap<String, String> dataDeviceName;
                try {
                    dataDeviceName = dataCommand.getData();
                    if (dataDeviceName != null) {
                        String name = dataDeviceName.get("deviceName");
                        this.deviceName = name;
                        servicePrefs.setDeviceName(name);
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
            case "set-device-pin":
                HashMap<String, String> dataDevicePin;
                try {
                    dataDevicePin = dataCommand.getData();
                    if (dataDevicePin != null) {
                        String str = dataDevicePin.get("devicePin");
                        this.devicePin = str;
                        servicePrefs.setDevicePassword(str);
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
            case "set-wallet-pin":
                HashMap<String, String> dataWalletPin;
                try {
                    dataWalletPin = dataCommand.getData();
                    if (dataWalletPin != null) {
                        String str = dataWalletPin.get("walletPin");
                        this.walletPin = str;
                        servicePrefs.setWalletPassword(str);
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
            case "check-recovery-phase-1":
                activity.showStatusFragment("Configuring Device", "Confirmation");
                callback.onResponse(response.toJson());
                return;
            case "check-recovery-phase-2":
                activity.showStatusFragment("Confirmation", "Confirm in your computer the order of the words as you wrote on the seed");
                callback.onResponse(response.toJson());
                return;
//            case "check-recovery-phase-finish":
//                activity.showLoadingFragment(true);
////                this.seeds = null;
////                this.isSeedGenerated = false;
//
//                        if (this.deviceName.isEmpty()) {
//                            this.deviceName = "test";
//                        }
//                        if (this.devicePin.isEmpty()) {
//                            this.devicePin = "1111";
//                        }
//                        if (this.walletPin.isEmpty()) {
//                            ProcessCommand.this.walletPin = "1111";
//                        }
//                        if (!ProcessCommand.this.deviceName.isEmpty() && !ProcessCommand.this.devicePin.isEmpty() && !ProcessCommand.this.walletPin.isEmpty()) {
//                            activity.showFinishFragment();
//                            activity.createWallet(seeds, walletPin, devicePin);
////                            response.status = true;
////                            callback.onResponse(response.toJson());
//                            ProcessCommand.this.seeds = null;
//                            ProcessCommand.this.isSeedGenerated = false;
//                        } else {
////                            response.status = false;
////                            response.data = "Missing some info";
////                            callback.onResponse(response.toJson());
//                        }
//
//
//                callback.onResponse(response.toJson());
//
//                return;
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
            case "check-recovery-phase-finish":
            case "show-finish":
                Log.d(LOG_TAG, "show-finish called");
//                this.deviceName = "test";
//                this.devicePin = "1111";
//                this.walletPin = "1111";
                if (this.deviceName.isEmpty()) {
                    this.deviceName = "test";
                }
                if (this.devicePin.isEmpty()) {
                    this.devicePin = "1111";
                }
                if (this.walletPin.isEmpty()) {
                    this.walletPin = "1111";
                }
                if (!this.deviceName.isEmpty() && !this.devicePin.isEmpty() && !this.walletPin.isEmpty()) {
                    activity.showFinishFragment();
                    activity.createWallet(seeds, walletPin, devicePin);
                    response.status = true;
                    callback.onResponse(response.toJson());
                    this.seeds = null;
                    this.isSeedGenerated = false;
                } else {
                    response.status = false;
                    response.data = "Missing some info";
                    callback.onResponse(response.toJson());
                }
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
                        for (WalletAccount account : accounts) {
//                            if (account.getCoinType() == BitcoinTest.get()) {
                                Log.d(LOG_TAG, account.getCoinType().toString() +": " + account.getReceiveAddress() + " lastConnectivity: " + account.getConnectivityStatus().toString());
//                            }
                        }
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

            case "make-transaction":
                if (activity.getWalletApplication().getWallet() != null) {
                    try {
                        HashMap<String, String> data1 = new HashMap<>();
                        data1 = dataCommand.getData();
                        String accId = data1.get("accountId");
                        final WalletAccount walletAccount = activity.getWalletApplication().getAccount(accId);
//                        Value amount = Value.valueOf( walletAccount.getCoinType() ,data1.get("amount"));
                        Value amount = getAmount(walletAccount.getCoinType(), data1.get("amount"));
//                        final String toAddress = data1.get("toAddress");
                        AbstractAddress toAddress = parseAddress(walletAccount, activity.getWalletApplication(),GenericUtils.fixAddress(data1.get("toAddress")));
                        activity.onMakeTransaction(walletAccount, toAddress, amount, null);
                        response.status = true;
                        callback.onResponse(response.toJson());
                    } catch (Exception e){
                        response.status = false;
                        response.data = e.getMessage();
                        callback.onResponse(response.toJson());
                    }
                } else {
                    response.status = false;
                    callback.onResponse(response.toJson());
                }
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
            wallet.address = account.getReceiveAddress(false).toString();
            wallet.type = account.getCoinType().getSymbol();
            wallet.value = account.getBalance().toString();
            wallet.name = account.getDescriptionOrCoinName();
            wallet.accountId = account.getId();
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

    public Value getAmount(ValueType type, String amountIn) {
        final String str = amountIn.trim();
        Value amount = null;

        try {
            if (!str.isEmpty()) {
                if (type != null) {
                    amount = format.parse(type, str);
                }
            }
        } catch (final Exception x) { /* ignored */ }

        return amount;
    }

    private AbstractAddress parseAddress(WalletAccount account, WalletApplication application, String addressStr) throws AddressMalformedException {

        if (account.getCoinType() instanceof NxtFamily) {
            return account.getCoinType().newAddress(addressStr);

        }
        List<CoinType> possibleTypes = GenericUtils.getPossibleTypes(addressStr);

        if (possibleTypes.size() == 1) {
            return possibleTypes.get(0).newAddress(addressStr);
        } else {
            // This address string could be more that one coin type so first check if this address
            // comes from an account to determine the type.
            List<WalletAccount> possibleAccounts = application.getAccounts(possibleTypes);
            AbstractAddress addressOfAccount = null;
            for (WalletAccount account1 : possibleAccounts) {
                AbstractAddress testAddress = account1.getCoinType().newAddress(addressStr);
                if (account1.isAddressMine(testAddress)) {
                    addressOfAccount = testAddress;
                    break;
                }
            }

            if (addressOfAccount != null) {
                // If address is from another account don't show a dialog. The type should not
                // change as we know 100% that is correct one.
                return addressOfAccount;
            }
        }
        return null;
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
        public String accountId;
    }
}
