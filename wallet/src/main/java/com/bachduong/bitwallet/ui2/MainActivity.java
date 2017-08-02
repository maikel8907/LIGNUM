package com.bachduong.bitwallet.ui2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.bachduong.bitwallet.R;
import com.bachduong.bitwallet.service.Server;
import com.bachduong.bitwallet.ui.AbstractWalletFragmentActivity;

import java.util.Map;

public class MainActivity extends AbstractWalletFragmentActivity implements SplashFragment.Listener,
        PinLoginFragment.Listener,
        ShowSeedFragment.Listener,
        ChooseModeFragment.Listener,
        FinishFragment.Listener{

    private int backPressedNum;
    private Server server;
    private ProcessCommand processCommand;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If we detected that this device is incompatible
        if (!getWalletApplication().getConfiguration().isDeviceCompatible()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.incompatible_device_warning_title)
                    .setMessage(R.string.incompatible_device_warning_message)
                    .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create().show();
        } else {
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
        }
    }

    public void showWelcomeFragment() {
        replaceFragment(new WelcomeFragment());
    }

    public void showChooseModeFragment() {
        replaceFragment(new ChooseModeFragment());
    }

    public void showPinFragment() {
        replaceFragment(new PinLoginFragment());
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                replaceFragment(StatusShowFragment.newInstance("Confirmation", "Confirm in your computer the order of the words as you wrote on the seed"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        replaceFragment(new StatusLoadingFragment());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                replaceFragment(new FinishFragment());
                            }
                        }, 6000);
                    }
                }, 6000);
            }
        }, 6000);
    }

    @Override
    public void onCancelSeed() {

    }

    @Override
    public void onSeedGenerated(String[] seeds) {
        //processCommand.setSeeds(seeds);
        processCommand.isSeedGenerated = true;
    }

    @Override
    public void onButtonClicked(int mode) {
        if (mode == ChooseModeFragment.MODE_CONFIG) {
            // Enter config screen
            replaceFragment(StatusShowFragment.newInstance("Welcome to Icellet Setup", "Setting up device"));

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    replaceFragment(new PinLoginFragment());
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            replaceFragment(new PinLoginFragment());
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    replaceFragment(StatusShowFragment.newInstance("Configuring Device", ""));
//
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            replaceFragment(ShowSeedFragment.newInstance());
//                                        }
//                                    }, 6000);
//                                }
//                            }, 6000);
//                        }
//                    }, 6000);
//                }
//            }, 6000);
        } else if  (mode == ChooseModeFragment.MODE_RESTORE) {
            // Enter restore screen

        }
    }

    @Override
    public void onFinish() {

    }
}
