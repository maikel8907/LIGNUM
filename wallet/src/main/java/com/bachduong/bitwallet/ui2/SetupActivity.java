package com.bachduong.bitwallet.ui2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bachduong.bitwallet.R;
import com.bachduong.bitwallet.service.Server;
import com.bachduong.bitwallet.ui.AbstractWalletFragmentActivity;

public class SetupActivity extends AbstractWalletFragmentActivity implements SplashFragment.Listener, PinLoginFragment.Listener, ShowSeedFragment.Listener{

    private int backPressedNum;
    private Server server;

    private Server.TransporterListener transporterListener = new Server.TransporterListener() {
        @Override
        public void onReceived(String receive, Server.TransporterListener callback) {
            receive = "Ping back after received \n" + receive;
            callback.onResponse(receive);
        }

        @Override
        public void onResponse(String response) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

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
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new SplashFragment())
                        .commit();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.removeListener(transporterListener);
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
        replaceFragment(PinLoginFragment.newInstance(PinLoginFragment.TYPE_SET_PASSWORD_STEP_1 ,args));
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
    public void onNextScreenSeed(String[] seeds) {
        replaceFragment(CheckSeedFragment.newInstance(seeds));
    }

    @Override
    public void onCancelSeed() {

    }
}
