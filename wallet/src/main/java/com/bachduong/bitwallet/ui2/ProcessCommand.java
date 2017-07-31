package com.bachduong.bitwallet.ui2;

import android.util.Log;

import com.bachduong.bitwallet.service.Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by duongtung on 7/31/17.
 */

public class ProcessCommand implements Server.TransporterListener {
    private static final String LOG_TAG = ProcessCommand.class.getSimpleName();

    private MainActivity activity;
    Pattern mPattern = Pattern.compile("content-custom: (.*)");

    public ProcessCommand(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceived(String receive, Server.TransporterListener callback) {

        String data;
        Matcher matcher = mPattern.matcher(receive);
        if(matcher.find())
        {
            data = matcher.group(1); // dont know what to place
            Log.d(LOG_TAG, "get data:" + data);
            callback.onResponse(data);
        } else {
            callback.onResponse("");
        }
    }

    @Override
    public void onResponse(String response) {

    }
}
