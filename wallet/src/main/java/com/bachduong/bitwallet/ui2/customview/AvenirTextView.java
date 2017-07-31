package com.bachduong.bitwallet.ui2.customview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by duongtung on 7/31/17.
 */

@SuppressLint("AppCompatCustomView")
public class AvenirTextView extends TextView {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AvenirTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public AvenirTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public AvenirTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public AvenirTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            String fontName = "AvenirLTStd-Roman.otf";
            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}