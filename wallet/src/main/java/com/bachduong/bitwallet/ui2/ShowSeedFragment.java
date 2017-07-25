package com.bachduong.bitwallet.ui2;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bachduong.bitwallet.Constants;
import com.bachduong.bitwallet.R;
import com.bachduong.core.wallet.Wallet;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSeedFragment extends Fragment {

    public static final int STEP = 6;

    private String[] seeds;
    private int currentStep;

    private View convertView;

    private Button buttonCancel, buttonPrevious, buttonNext;
    private LinearLayout mSeedListLayout;
    private boolean isConfirm;

    private Listener listener;

    public ShowSeedFragment() {
        // Required empty public constructor
    }

    public static ShowSeedFragment newInstance() {

        Bundle args = new Bundle();

        ShowSeedFragment fragment = new ShowSeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowSeedFragment newInstance(int currentStep, String[] seeds) {

        Bundle args = new Bundle();

        ShowSeedFragment fragment = new ShowSeedFragment();
        fragment.setArguments(args);
        fragment.seeds = seeds;
        fragment.currentStep = currentStep;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertView = inflater.inflate(R.layout.fragment_show_seed_ui2, container, false);

//        buttonCancel = (Button) convertView.findViewById(R.id.button_cancel);
        buttonPrevious = (Button) convertView.findViewById(R.id.button_prev);
        buttonNext = (Button) convertView.findViewById(R.id.button_confirm);

//        buttonCancel.setOnClickListener(getButtonCancelListener());
        buttonNext.setOnClickListener(getButtonNextListener());
        buttonPrevious.setOnClickListener(getButtonPreviousListener());

        mSeedListLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutSeed);

        if (currentStep == 0) {
            generateNewMnemonic();
        }

        showSeed(currentStep, currentStep + STEP);

        return convertView;
    }

    private View.OnClickListener getButtonCancelListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCancelSeed();
                }
            }
        };
    }
    private View.OnClickListener getButtonPreviousListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep >= STEP) {
                    showSeed(currentStep - STEP, currentStep - STEP*2);
                }
            }
        };
    }
    private View.OnClickListener getButtonNextListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep < seeds.length) {
                    showSeed(currentStep, currentStep + STEP);
                }
                Log.d("TUNG", "currentStep=" + String.valueOf(currentStep));
//                if (isConfirm) {
//                    if (listener != null) {
//                        listener.onNextScreenSeed(seeds);
//                    }
//                }
            }
        };
    }

    private View.OnClickListener getButtonConfirmListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onNextScreenSeed(seeds);
                }
            }
        };
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

    private void generateNewMnemonic() {

        String mnemonic;
        mnemonic = Wallet.generateMnemonicString(Constants.SEED_ENTROPY_DEFAULT);
        seeds = mnemonic.split(" ");

    }

    private void showSeed(int currentStep, int nextStep) {
        mSeedListLayout.removeAllViews();
        for (int i = currentStep; i < currentStep + STEP; i++) {
            TextView textview = new TextView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textview.setLayoutParams(layoutParams);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textview.setTextAppearance(R.style.TextAppearance_AppCompat_Medium);
            }
            textview.setTextColor(getResources().getColor(R.color.primary_500));
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
            String s = String.valueOf(i + 1) + ". " + seeds[i];
            textview.setText(s);
            mSeedListLayout.addView(textview);
        }
        if (nextStep < seeds.length && nextStep >= 0) {
            this.currentStep = nextStep;
        }
        if (nextStep >= seeds.length) {
            buttonNext.setText(R.string.action_confirm);
            isConfirm = true;
            buttonNext.setOnClickListener(getButtonConfirmListener());
        } else {
            buttonNext.setText(R.string.action_next);
            isConfirm = false;
            buttonNext.setOnClickListener(getButtonNextListener());
        }
        if (nextStep <= 0) {
            buttonPrevious.setVisibility(View.INVISIBLE);
        } else {
            buttonPrevious.setVisibility(View.VISIBLE);
        }
    }

    public interface Listener {
        void onNextScreenSeed(String[] seeds);
        void onCancelSeed();
    }
}
