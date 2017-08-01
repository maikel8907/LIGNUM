package com.bachduong.bitwallet.ui2.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bachduong.bitwallet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by duongtung on 7/17/17.
 */

public class PasswordInputView extends GridLayout {
    private static final String KEY_ID = "key_id_";

    private Context context;
    private EditText editText;
    //    private String[] keys = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String[] keys = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private List<String> keyList = new ArrayList<>(Arrays.asList(keys));
    private Listener listener;
    private LayoutInflater inflater;


    public PasswordInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        inflater = LayoutInflater.from(context);
        addKeyButton();
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflater = LayoutInflater.from(context);
        addKeyButton();
    }

    public PasswordInputView(Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
        addKeyButton();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void setOnTextChangeListener(Listener listener) {
        this.listener = listener;
    }

    private Button getButton(final String keyNum) {
        Button button = (Button) inflater.inflate(R.layout.keyboard_button, this, false);
        button.setText(String.valueOf(keyNum));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText != null) {

                    String text = editText.getText().toString() + keyNum;
                    editText.setText(text);
                    if (listener != null) {
                        listener.onTextChanged();
                    }
                }
            }
        });

        return button;
    }

    private ImageButton getBackSpace() {
        ImageButton button = new ImageButton(context);
        button.setLayoutParams(
                new LinearLayout.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.key_board_size),
                        context.getResources().getDimensionPixelSize(R.dimen.key_board_size)));
        //button.setGravity(Gravity.CENTER);
        button.setBackgroundResource(R.drawable.key_board_background);
        //button.setTextSize(TypedValue.COMPLEX_UNIT_SP , R.dimen.key_board_text_size);
        button.setPadding(0, 0, 0, 0);
        button.setImageResource(R.drawable.ic_keyboard_backspace_black_24dp);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText != null) {
                    if (editText.getText().length() > 0) {
                        String text = editText.getText().delete(editText.getText().length() - 1, editText.getText().length()).toString();
                        editText.setText(text);
                        if (listener != null) {
                            listener.onTextChanged();
                        }
                    }
                }
            }
        });
        return button;
    }

    private void addKeyButton() {
        long seed = System.nanoTime();
        Collections.shuffle(keyList, new Random(seed));
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            Button button = getButton(keyList.get(i));
            addView(button);
        }
    }

    public Map<Integer, String> getKeyMap() {
        Map<Integer, String> map = new HashMap<>();
        int size = keyList.size();
        for (int i = 0; i< size; i++) {
            map.put(i, keyList.get(i));
        }
        Map<Integer, String> treeMap = new TreeMap<Integer, String>(map);
        return treeMap;
    }
    public interface Listener {
        void onTextChanged();
    }
}
