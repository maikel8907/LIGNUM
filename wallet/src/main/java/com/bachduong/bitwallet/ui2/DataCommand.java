package com.bachduong.bitwallet.ui2;

import java.util.HashMap;

/**
 * Created by duongtung on 8/1/17.
 */

public class DataCommand {
    private String command;
    private HashMap<String, String> data;

    public DataCommand() {
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
