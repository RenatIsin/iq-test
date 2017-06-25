package com.iqoptions.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by MiF on 24.06.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrayMessageResponse extends Response {

    private String[] message;

    public String message() {
        return message[0];
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
