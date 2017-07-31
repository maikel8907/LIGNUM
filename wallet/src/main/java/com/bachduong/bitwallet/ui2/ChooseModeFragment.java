package com.bachduong.bitwallet.ui2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bachduong.bitwallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseModeFragment extends Fragment {

    public static final int MODE_CONFIG = 0;
    public static final int MODE_RESTORE = 1;
    private Listener listener;

    private Button buttonConfig, buttonRestore;

    public ChooseModeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_mode, container, false);
        buttonConfig = (Button) view.findViewById(R.id.button_config);
        buttonRestore = (Button) view.findViewById(R.id.button_restore);
        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onButtonClicked(MODE_CONFIG);
                }
            }
        });
        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onButtonClicked(MODE_RESTORE);
                }
            }
        });
        return view;
    }

    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + ChooseModeFragment.Listener.class);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface Listener {
        void onButtonClicked(int mode);
    }
}
