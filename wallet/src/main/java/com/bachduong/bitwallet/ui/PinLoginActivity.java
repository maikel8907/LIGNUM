package com.bachduong.bitwallet.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bachduong.bitwallet.R;
import com.bachduong.bitwallet.ui.customview.PinEntryView;


public class PinLoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = PinLoginActivity.class.getSimpleName();

    EditText editTextPin;

    Button buttonSignIn, button1, button2, button3,
        button4, button5 , button6, button7, button8, button9, button0;
    ImageButton backSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);
        editTextPin = (EditText) findViewById(R.id.edit_text_password);
        editTextPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    public void onClick(View view) {
        int id = view.getId();
        String s = "";
        switch (id) {
            case R.id.button_0:
                s = "0";
                break;
            case R.id.button_1:
                s = "1";
                break;
            case R.id.button_2:
                s= "2";
                break;
            case R.id.button_3:
                s= "3";
                break;
            case R.id.button_4:
                s= "4";
                break;
            case R.id.button_5:
                s= "5";
                break;
            case R.id.button_6:
                s= "6";
                break;
            case R.id.button_7:
                s= "7";
                break;
            case R.id.button_8:
                s= "8";
                break;
            case R.id.button_9:
                s= "9";
                break;
        }
        editTextPin.setText(editTextPin.getText().toString() + s);
    }

    public void onClickBack(View view) {
        if (editTextPin.getText().length() > 0) {
            String s = editTextPin.getText().delete(editTextPin.getText().length() - 1, editTextPin.getText().length()).toString();
            editTextPin.setText(s);
        }
    }

    public void onClickSignIn(View view) {
        attemptLogin();
    }

    private void attemptLogin() {
        String pin = editTextPin.getText().toString();
        //Tung Duong todo: need to code this function for checking valid PIN

        //This toast just for test


        Toast.makeText(PinLoginActivity.this, "You can login now, this PIN checking not complete yet. Input pin is " + pin, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PinLoginActivity.this, WalletActivity.class));

        finish();
    }
}
