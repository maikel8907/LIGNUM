package com.bachduong.bitwallet.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bachduong.bitwallet.R;
import com.bachduong.bitwallet.ui.customview.PinEntryView;


public class PinLoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = PinLoginActivity.class.getSimpleName();
    PinEntryView pinEntryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);
        pinEntryView = (PinEntryView) findViewById(R.id.pin_entry_simple);

        pinEntryView.setOnPinEnteredListener(new PinEntryView.OnPinEnteredListener() {
            @Override
            public void onPinEntered(String pin) {
                checkPin(pin);
            }
        });
    }

    private void checkPin(String pin) {
        //Tung Duong todo: need to code this function for checking valid PIN

        //This toast just for test

        Toast.makeText(PinLoginActivity.this, "You can login now, this PIN checking not complete yet", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PinLoginActivity.this, WalletActivity.class));
    }
}
