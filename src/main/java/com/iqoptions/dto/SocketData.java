package com.iqoptions.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * Created by MiF on 26.06.2017.
 */
public class SocketData {
    private String name;
    private Object msg;

    public static SocketData ssid(String ssid){
        return new SocketData("ssid", ssid);
    }

    public SocketData() {
    }

    public SocketData(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
