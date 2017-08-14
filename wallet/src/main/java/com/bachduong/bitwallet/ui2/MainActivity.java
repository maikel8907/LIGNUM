package com.bachduong.bitwallet.ui2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bachduong.bitwallet.Constants;
import com.bachduong.bitwallet.R;
import com.bachduong.bitwallet.WalletApplication;
import com.bachduong.bitwallet.service.CoinService;
import com.bachduong.bitwallet.service.CoinServiceImpl;
import com.bachduong.bitwallet.service.Server;
import com.bachduong.bitwallet.util.WeakHandler;
import com.bachduong.core.coins.CoinType;
import com.bachduong.core.wallet.Wallet;
import com.google.firebase.crash.FirebaseCrash;

import org.bitcoinj.crypto.KeyCrypterScrypt;
import org.spongycastle.crypto.params.KeyParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends FragmentActivity implements SplashFragment.Listener,
        PinLoginFragment.Listener,
        ShowSeedFragment.Listener,
        ChooseModeFragment.Listener,
        FinishFragment.Listener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int RESTORE_STATUS_UPDATE = 0;
    private static final int RESTORE_FINISHED = 1;

    private int backPressedNum;
    private Server server;
    private ProcessCommand processCommand;


    private final Handler handler = new MyHandler(this);

    private static WalletFromSeedTask walletFromSeedTask;

    private Server.TransporterListener transporterListener = new Server.TransporterListener() {
        @Override
        public void onReceived(String receive, Server.TransporterListener callback) {
            receive = receive + "\r\nPing back after received \n";
            String response =
                    "<p>\n" +
                            "{status : true; data : {})" +
                            "</p>\n";
            //callback.onResponse(response);
        }

        @Override
        public void onResponse(String response) {

        }
    };

    protected WalletApplication getWalletApplication() {
        return (WalletApplication) getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // If we detected that this device is incompatible
//        if (!getWalletApplication().getConfiguration().isDeviceCompatible()) {
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.incompatible_device_warning_title)
//                    .setMessage(R.string.incompatible_device_warning_message)
//                    .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    })
//                    .setCancelable(false)
//                    .create().show();
//        } else {
            server = new Server();
            server.addListener(transporterListener);
            processCommand = new ProcessCommand(this);
            server.addListener(processCommand);
            if (savedInstanceState == null) {

                showWelcomeFragment();

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        replaceFragment(new ChooseModeFragment());
//                    }
//                }, 5000);
            }
//        }
        addShortcut();
    }
    private void addShortcut() {
        Intent shortcutIntent = new Intent(getApplicationContext(),
                MainActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.drawable.ic_launcher_dev));

        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);  //may it's already there so don't duplicate
        getApplicationContext().sendBroadcast(addIntent);
    }
    public void showWelcomeFragment() {
        replaceFragment(new WelcomeFragment());
    }

    public void showChooseModeFragment() {
        replaceFragment(new ChooseModeFragment());
    }

    public void showPinFragment(PinLoginFragment.Listener callback) {
        replaceFragment(PinLoginFragment.newInstance(callback));
    }

    public void showStatusFragment(String title, String content) {
        replaceFragment(StatusShowFragment.newInstance(title, content));
    }

    public void showSeedFragment() {
        replaceFragment(ShowSeedFragment.newInstance());
    }

    public void showLoadingFragment(final boolean showFinishAfter) {
        replaceFragment(new StatusLoadingFragment());
        if (showFinishAfter) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showFinishFragment();
//                }
//            }, 6000);
        }
    }

    public void showFinishFragment() {
        replaceFragment(new FinishFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.removeListener(transporterListener);
            server.removeListener(processCommand);
            server.onDestroy();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (backPressedNum == 1) {
            finish();
        } else {

            Toast.makeText(this, R.string.setup_activity_back_press_toast, Toast.LENGTH_SHORT).show();
            backPressedNum++;

        }

    }

    @Override
    public void onSplashFinish(Bundle args) {
        //replaceFragment(PinLoginFragment.newInstance(PinLoginFragment.TYPE_SET_PASSWORD_STEP_1, args));
        replaceFragment(new WelcomeFragment());
    }

    @Override
    public void onPasswordSetStep1(String password) {
        replaceFragment(PinLoginFragment.newInstance(PinLoginFragment.TYPE_SET_PASSWORD_STEP_2, password, getIntent().getExtras()));
    }

    @Override
    public void onPasswordSetFinal(String password) {

        Toast.makeText(this, "Set password " + password, Toast.LENGTH_SHORT).show();
        replaceFragment(ShowSeedFragment.newInstance());
    }

    @Override
    public void onLoginSuccess(Bundle agrs) {

    }

    @Override
    public void onFinishLoadKeyMap(Map<Integer, String> keyMap) {
        processCommand.setCurrentKeyMap(keyMap);
    }

    @Override
    public void onNextScreenSeed(String[] seeds) {
        processCommand.setSeeds(seeds);

        replaceFragment(StatusShowFragment.newInstance("Configuring Device", "Confirmation"));

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                replaceFragment(StatusShowFragment.newInstance("Confirmation", "Confirm in your computer the order of the words as you wrote on the seed"));
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        replaceFragment(new StatusLoadingFragment());
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                replaceFragment(new FinishFragment());
//                            }
//                        }, 6000);
//                    }
//                }, 6000);
//            }
//        }, 6000);
    }

    @Override
    public void onCancelSeed() {

    }

    @Override
    public void onSeedGenerated(String[] seeds) {
        //processCommand.setSeeds(seeds);
        if (seeds != null && seeds.length > 0) {
            processCommand.isSeedGenerated = true;
        } else {
            processCommand.isSeedGenerated = false;
        }
    }

    @Override
    public void onButtonClicked(int mode) {
        if (mode == ChooseModeFragment.MODE_CONFIG) {
            // Enter config screen
            replaceFragment(StatusShowFragment.newInstance("Welcome to Icellet Setup", "Setting up device"));
            processCommand.chooseMode = 1;
        } else if (mode == ChooseModeFragment.MODE_RESTORE) {
            // Enter restore screen
            processCommand.chooseMode = 2;
        }
    }

    @Override
    public void onFinish() {

    }

    public void createWallet(String[] seeds, String walletPassword, String seedPassword) {
        Log.d(LOG_TAG, "create wallet called");
        String seed = TextUtils.join(" ", seeds);
        List<CoinType> coinsToCreate = Constants.SUPPORTED_COINS;

        if (walletFromSeedTask == null) {
            walletFromSeedTask = new WalletFromSeedTask(handler, getWalletApplication(), coinsToCreate, seed, walletPassword, seedPassword);
            walletFromSeedTask.execute();
        } else {
            switch (walletFromSeedTask.getStatus()) {
                case FINISHED:
                    handler.sendEmptyMessage(RESTORE_FINISHED);
                    break;
                case RUNNING:
                case PENDING:
                    walletFromSeedTask.handler = handler;
            }
        }
    }

    static class WalletFromSeedTask extends AsyncTask<Void, String, Wallet> {
        Wallet wallet;
        String errorMessage = "";
        private final String seed;
        private final String password;
        @Nullable
        private final String seedPassword;
        Handler handler;
        private final WalletApplication walletApplication;
        private final List<CoinType> coinsToCreate;

        public WalletFromSeedTask(Handler handler, WalletApplication walletApplication, List<CoinType> coinsToCreate, String seed, String password, @Nullable String seedPassword) {
            this.handler = handler;
            this.walletApplication = walletApplication;
            this.coinsToCreate = coinsToCreate;
            this.seed = seed;
            this.password = password;
            this.seedPassword = seedPassword;
        }

        protected Wallet doInBackground(Void... params) {
            Intent intent = new Intent(CoinService.ACTION_CLEAR_CONNECTIONS, null,
                    walletApplication, CoinServiceImpl.class);
            walletApplication.startService(intent);

            ArrayList<String> seedWords = new ArrayList<String>();
            for (String word : seed.trim().split(" ")) {
                if (word.isEmpty()) continue;
                seedWords.add(word);
            }

            try {
                this.publishProgress("");
                walletApplication.setEmptyWallet();
                wallet = new Wallet(seedWords, seedPassword);
                KeyParameter aesKey = null;
                if (password != null && !password.isEmpty()) {
                    KeyCrypterScrypt crypter = new KeyCrypterScrypt();
                    aesKey = crypter.deriveKey(password);
                    wallet.encrypt(crypter, aesKey);
                }

                for (CoinType type : coinsToCreate) {
                    this.publishProgress(type.getName());
                    wallet.createAccount(type, false, aesKey);
                }

                walletApplication.setWallet(wallet);
                walletApplication.saveWalletNow();
            } catch (Exception e) {
                Log.e("Error creating a wallet", e.getMessage());
                errorMessage = e.getMessage();
            }
            return wallet;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            handler.sendMessage(handler.obtainMessage(RESTORE_STATUS_UPDATE, values[0]));
        }

        protected void onPostExecute(Wallet wallet) {
            handler.sendEmptyMessage(RESTORE_FINISHED);
        }
    }

    private static class MyHandler extends WeakHandler<MainActivity> {
        public MyHandler(MainActivity ref) { super(ref); }

        @Override
        protected void weakHandleMessage(MainActivity ref, Message msg) {
            switch (msg.what) {
                case RESTORE_STATUS_UPDATE:
                    String workingOn = (String) msg.obj;
                    if (workingOn.isEmpty()) {
                        Log.d(LOG_TAG, "Status: " + ref.getString(R.string.wallet_restoration_master_key));
                        //ref.status.setText(ref.getString(R.string.wallet_restoration_master_key));
                    } else {
                        //ref.status.setText(ref.getString(R.string.wallet_restoration_coin, workingOn));
                        Log.d(LOG_TAG, "Status: " + ref.getString(R.string.wallet_restoration_master_key));
                    }
                    break;
                case RESTORE_FINISHED:
                    WalletFromSeedTask task = walletFromSeedTask;
                    walletFromSeedTask = null;
                    if (task.wallet != null) {
                        //ref.startWalletActivity();
                        Log.d(LOG_TAG, "Status: startWalletActivity()");
                    } else {
                        String errorMessage = ref.getResources().getString(
                                R.string.wallet_restoration_error, task.errorMessage);
                        //ref.showErrorAndStartIntroActivity(errorMessage);
                        Log.d(LOG_TAG, "Error: " + errorMessage);
                    }
            }
        }
    }
}
