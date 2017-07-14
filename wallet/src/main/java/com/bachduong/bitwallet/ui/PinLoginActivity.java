package com.bachduong.bitwallet.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bachduong.bitwallet.R;
import com.bachduong.bitwallet.util.PasswordQualityChecker;
import com.bachduong.bitwallet.util.WalletUtils;


public class PinLoginActivity extends BaseWalletActivity {
    private static final String LOG_TAG = PinLoginActivity.class.getSimpleName();

    private EditText editTextPin;
    private TextView errorPassword;
    private int maxWrongPin = 3;
    private PasswordQualityChecker passwordQualityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);

        if (getWalletApplication().getWallet() == null) {
            startIntro();
            finish();
            return;
        }
        passwordQualityChecker = new PasswordQualityChecker(this);

        editTextPin = (EditText) findViewById(R.id.edit_text_password);
        errorPassword = (TextView) findViewById(R.id.password_error);

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

        clearError(errorPassword);
    }

    public void onClick(View view) {
        clearError(errorPassword);
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
                s = "2";
                break;
            case R.id.button_3:
                s = "3";
                break;
            case R.id.button_4:
                s = "4";
                break;
            case R.id.button_5:
                s = "5";
                break;
            case R.id.button_6:
                s = "6";
                break;
            case R.id.button_7:
                s = "7";
                break;
            case R.id.button_8:
                s = "8";
                break;
            case R.id.button_9:
                s = "9";
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


//        //This toast just for test
//        Toast.makeText(PinLoginActivity.this, "You can login now, this PIN checking not complete yet. Input pin is " + pin, Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(PinLoginActivity.this, WalletActivity.class));
        if (!checkPasswordQuality(pin)) {
            editTextPin.setText("");
            return;
        }
        if (WalletUtils.checkWalletPassword(this, pin)) {
            startWallet();
        } else {
            //Tung Duong todo: need to put all string in to Strings Resource
            if (maxWrongPin == 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Wrong password !")
                        .setMessage("You already input 3 times wrong password.")
                        .setNeutralButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
            } else {

                editTextPin.setText("");
//                Toast.makeText(
//                        PinLoginActivity.this,
//                        "Wrong PIN, you can try in " + maxWrongPin + " times more",
//                        Toast.LENGTH_SHORT).show();
                setError(errorPassword, "Wrong PIN, you can try in " + maxWrongPin + " times more.");
                maxWrongPin--;
            }
        }

    }

    private boolean checkPasswordQuality(String pass) {

        try {
            passwordQualityChecker.checkPassword(pass);

            clearError(errorPassword);
        } catch (PasswordQualityChecker.PasswordTooCommonException e1) {

        } catch (PasswordQualityChecker.PasswordTooShortException e2) {

            setError(errorPassword, R.string.password_too_short_error,
                    passwordQualityChecker.getMinPasswordLength());
            return false;
        }
        return true;
    }

    private void setError(TextView errorView, int messageId, Object... formatArgs) {
        setError(errorView, getResources().getString(messageId, formatArgs));
    }

    private void setError(TextView errorView, String message) {
        errorView.setText(message);
        showError(errorView);
    }
    private void showError(TextView errorView) {
        errorView.setVisibility(View.VISIBLE);
    }

    private void clearError(TextView errorView) {
        errorView.setVisibility(View.GONE);
    }
    private void startIntro() {
        Intent introIntent = new Intent(this, IntroActivity.class);
        startActivity(introIntent);
    }

    private void startWallet() {
        Intent walletIntent = new Intent(this, WalletActivity.class);
        startActivity(walletIntent);
    }
}
