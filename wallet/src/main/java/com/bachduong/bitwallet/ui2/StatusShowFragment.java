package com.bachduong.bitwallet.ui2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bachduong.bitwallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusShowFragment extends Fragment {

    private TextView textViewTitle;
    private TextView textViewContent;

    private String title;
    private String content;

    public StatusShowFragment() {
        // Required empty public constructor
    }

    public static StatusShowFragment newInstance(String title, String content) {

        Bundle args = new Bundle();

        StatusShowFragment fragment = new StatusShowFragment();
        fragment.setArguments(args);
        fragment.title = title;
        fragment.content = content;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_show, container, false);
        textViewTitle = (TextView) view.findViewById(R.id.text_view_title);
        textViewContent = (TextView) view.findViewById(R.id.text_view_content);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        textViewTitle.setText(title);
        textViewContent.setText(content);
        if (title.isEmpty()) {
            textViewTitle.setVisibility(View.GONE);
        }
        if (content.isEmpty()) {
            textViewContent.setVisibility(View.GONE);
        }

    }

}
