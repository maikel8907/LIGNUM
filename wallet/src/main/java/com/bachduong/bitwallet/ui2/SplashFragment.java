package com.bachduong.bitwallet.ui2;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bachduong.bitwallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private Listener listener;
    private View convertView;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {

        Bundle args = new Bundle();

        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertView = inflater.inflate(R.layout.fragment_splash, container, false);

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onSplashFinish(getArguments());
                }
            }
        }, 2000);

        return convertView;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            listener = (SplashFragment.Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + SplashFragment.Listener.class);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface Listener {
        void onSplashFinish(Bundle args);
    }

}
