package com.bachduong.bitwallet.ui2;

/**
 * Created by duongtung on 8/1/17.
 */

public class DataCommand {
    private String command;
    private Object data;

    public DataCommand() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
