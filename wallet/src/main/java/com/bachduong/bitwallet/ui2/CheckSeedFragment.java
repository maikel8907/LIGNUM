package com.bachduong.bitwallet.ui2;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckSeedFragment extends Fragment {

    private String[] seeds;

    private View convertView;
    private TextView textViewNumber, textViewError;
    private EditText editTextWord;
    private Button buttonConfirm;

    private int currentPosition;

    public CheckSeedFragment() {
        // Required empty public constructor
    }

    public static CheckSeedFragment newInstance() {

        Bundle args = new Bundle();

        CheckSeedFragment fragment = new CheckSeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CheckSeedFragment newInstance(String[] seeds) {

        Bundle args = new Bundle();

        CheckSeedFragment fragment = new CheckSeedFragment();
        fragment.setArguments(args);
        fragment.seeds = seeds;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertView = inflater.inflate(R.layout.fragment_check_seed, container, false);
        textViewNumber = (TextView) convertView.findViewById(R.id.text_view_number);
        textViewError = (TextView) convertView.findViewById(R.id.text_view_error);
        editTextWord = (EditText) convertView.findViewById(R.id.edit_text_word);
        buttonConfirm = (Button) convertView.findViewById(R.id.button_confirm);

        editTextWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptCheck();
                    return true;
                }
                clearError(textViewError);
                return false;
            }
        });
        editTextWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                clearError(textViewError);
                return false;
            }
        });
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCheck();
            }
        });

        setUI(currentPosition);
        clearError(textViewError);
        return convertView;
    }

    private void setUI(int currentPosition) {
        String label = String.format(getString(R.string.check_seed_label_number), intToPos(currentPosition+1));
        textViewNumber.setText(label);
        editTextWord.setText("");
    }

    private void attemptCheck() {
        String word = editTextWord.getText().toString();

        if (word.equals(seeds[currentPosition])) {
            //Toast.makeText(getActivity(), "true", Toast.LENGTH_SHORT).show();
            currentPosition++;
            setUI(currentPosition);
        } else {
            //Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
            setError(textViewError, R.string.check_seed_false);
            setUI(currentPosition);
        }

    }

    private String intToPos(int position) {
        String s;
        switch (position) {
            case 1:
                s = String.valueOf(position) + "st";
                break;
            case 2:
                s = String.valueOf(position) + "nd";
                break;
            case 3:
                s = String.valueOf(position) + "rd";
                break;
            default:
                s = String.valueOf(position) + "th";
        }

        return s;
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

}
