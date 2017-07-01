package com.iqoptions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by MiF on 24.06.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private boolean isSuccessful;
    private Message message;
    private int code;
    private int[] codeList;
    private Object result;


    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int[] getCodeList() {
        return codeList;
    }

    public void setCodeList(int[] codeList) {
        this.codeList = codeList;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
