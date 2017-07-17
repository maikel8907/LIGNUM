package com.bachduong.bitwallet.ui2;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bachduong.bitwallet.R;
import com.bachduong.bitwallet.ui2.customview.PasswordInputView;
import com.bachduong.bitwallet.util.PasswordQualityChecker;
import com.bachduong.bitwallet.util.WalletUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinLoginFragment extends Fragment {

    public static final int TYPE_LOGIN = 0;
    public static final int TYPE_SET_PASSWORD_STEP_1 = 1;
    public static final int TYPE_SET_PASSWORD_STEP_2 = 2;

    private View convertView;
    private EditText editTextPin;
    private TextView errorPassword;
    private TextView textViewLabel;

    private Button buttonLogin;
    private int maxWrongPin = 3;
    private PasswordQualityChecker passwordQualityChecker;
    private PasswordInputView passwordView;
    private String prevStepPassword;

    private int mType;
    private Listener listener;

    public PinLoginFragment() {
        // Required empty public constructor
    }

    public static PinLoginFragment newInstance(Bundle args) {
        PinLoginFragment fragment = new PinLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PinLoginFragment newInstance(int type, Bundle args) {
        PinLoginFragment fragment = new PinLoginFragment();
        fragment.setArguments(args);
        fragment.mType = type;
        return fragment;
    }

    public static PinLoginFragment newInstance(int type, String step1Password, Bundle args) {
        PinLoginFragment fragment = new PinLoginFragment();
        fragment.setArguments(args);
        fragment.mType = type;
        fragment.prevStepPassword = step1Password;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertView = inflater.inflate(R.layout.fragment_pin_login, container, false);
        passwordQualityChecker = new PasswordQualityChecker(getActivity());

        editTextPin = (EditText) convertView.findViewById(R.id.edit_text_password);
        errorPassword = (TextView) convertView.findViewById(R.id.password_error);
        passwordView = (PasswordInputView) convertView.findViewById(R.id.password_view);
        passwordView.setEditText(editTextPin);
        passwordView.setOnTextChangeListener(new PasswordInputView.Listener() {
            @Override
            public void onTextChanged() {
                clearError(errorPassword);
            }
        });

        textViewLabel = (TextView) convertView.findViewById(R.id.text_view_label);
        switch (mType) {
            case 0:
                textViewLabel.setText(R.string.pin_login_activity_enter_pin);
                break;
            case 1:
                textViewLabel.setText(R.string.pin_login_set_password);
                break;
            case 2:
                textViewLabel.setText(R.string.pin_login_set_password_re_enter);
                break;
        }

        buttonLogin = (Button) convertView.findViewById(R.id.button_sign_in);

        switch (mType) {
            case 0:
                buttonLogin.setText(R.string.action_sign_in);
                break;
            case 1:
                buttonLogin.setText(R.string.action_next);
                break;
            case 2:
                buttonLogin.setText(R.string.action_confirm);
                break;
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mType) {
                    case 0:
                        attemptLogin();
                        break;
                    case 1:
                        setPasswordStep1();
                        break;
                    case 2:
                        setPasswordStep2();
                        break;
                }
            }
        });

        clearError(errorPassword);
        return convertView;
    }
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + SplashFragment.Listener.class);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void setPasswordStep1() {

        String pin = editTextPin.getText().toString();
        if (!checkPasswordQuality(pin)) {
            editTextPin.setText("");
            return;
        }

        if (listener != null) {
            listener.onPasswordSetStep1(pin);
        }

    }

    private void setPasswordStep2() {

        String pin = editTextPin.getText().toString();
        if (!checkPasswordQuality(pin)) {
            editTextPin.setText("");
            return;
        }
        if (!pin.equals(prevStepPassword)) {
            editTextPin.setText("");
            setError(errorPassword, R.string.passwords_mismatch);
            return;
        }

        if (listener != null) {
            listener.onPasswordSetFinal(pin);
        }

    }

    private void attemptLogin() {
        String pin = editTextPin.getText().toString();


//        //This toast just for test
        Toast.makeText(getActivity(), "Input pin is " + pin, Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(PinLoginActivity.this, WalletActivity.class));
        if (!checkPasswordQuality(pin)) {
            editTextPin.setText("");
            return;
        }
        if (WalletUtils.checkWalletPassword(getActivity(), pin)) {
            //startWallet();
        } else {
            //Tung Duong todo: need to put all string in to Strings Resource
            if (maxWrongPin == 0) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Wrong password !")
                        .setMessage("You already input 3 times wrong password.")
                        .setNeutralButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        }).create().show();
            } else {

                editTextPin.setText("");
//                Toast.makeText(
//                        PinLoginActivity.this,
//                        "Wrong PIN, you can try in " + maxWrongPin + " times more",
//                        Toast.LENGTH_SHORT).show();
                setError(errorPassword, "Wrong PIN, you can try in " + maxWrongPin + " times more." + "\n Please enter test pass 2409");
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

    public interface Listener {
        void onPasswordSetStep1(String password);

        void onPasswordSetFinal(String password);

        void onLoginSuccess(Bundle agrs);
    }
}
