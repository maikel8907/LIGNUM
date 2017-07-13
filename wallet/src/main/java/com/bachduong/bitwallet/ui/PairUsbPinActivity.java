package com.bachduong.bitwallet.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bachduong.bitwallet.Constants;
import com.bachduong.bitwallet.R;

import static org.bitcoinj.core.TransactionBroadcast.random;

public class PairUsbPinActivity extends AppCompatActivity {

    private ImageView imageViewPosition1, imageViewPosition2, imageViewPosition3, imageViewPosition4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_usb_pin);

        imageViewPosition1 = (ImageView) findViewById(R.id.image_view_pos_one);
        imageViewPosition2 = (ImageView) findViewById(R.id.image_view_pos_two);
        imageViewPosition3 = (ImageView) findViewById(R.id.image_view_pos_three);
        imageViewPosition4 = (ImageView) findViewById(R.id.image_view_pos_four);

        showPairOnScreen(generateRandomPairPin());
    }

    // Tung Duong todo: need to make number of imageViews dynamic, can add dynamic imageView
    private void showPairOnScreen(String pairPin) {

        showDigitOnImageView(pairPin.charAt(0), imageViewPosition1);
        showDigitOnImageView(pairPin.charAt(1), imageViewPosition2);
        showDigitOnImageView(pairPin.charAt(2), imageViewPosition3);
        showDigitOnImageView(pairPin.charAt(3), imageViewPosition4);
    }

    private void showDigitOnImageView(char digit, ImageView imageView) {
        int drawableId = Constants.PIN_DIGIT_0;
        String str = String.valueOf(digit);
        switch (str) {
            case "0":
                drawableId = Constants.PIN_DIGIT_0;
                break;
            case "1":
                drawableId = Constants.PIN_DIGIT_1;
                break;
            case "2":
                drawableId = Constants.PIN_DIGIT_2;
                break;
            case "3":
                drawableId = Constants.PIN_DIGIT_3;
                break;
            case "4":
                drawableId = Constants.PIN_DIGIT_4;
                break;
            case "5":
                drawableId = Constants.PIN_DIGIT_5;
                break;
            case "6":
                drawableId = Constants.PIN_DIGIT_6;
                break;
            case "7":
                drawableId = Constants.PIN_DIGIT_7;
                break;
            case "8":
                drawableId = Constants.PIN_DIGIT_8;
                break;
            case "9":
                drawableId = Constants.PIN_DIGIT_9;
                break;
        }
        imageView.setImageResource(drawableId);
    }

    // Tung Duong todo: need to put this function in to security class later
    public String generateRandomPairPin() {
        String value = "";
        int numberOfDigit = 4;
        for (int i = 0; i < numberOfDigit; i++) {
            int ran = random.nextInt(10) + 1;
            value += String.valueOf(ran);
        }
        return value;
    }
}
