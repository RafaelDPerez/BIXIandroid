package com.bixi.bixi.Pojos;


import java.io.Serializable;

public class CreateUserResponse implements Serializable {
    private int sceResponseCode;
    private String sceResponseMsg;
    private final static long serialVersionUID = 4007865184775523488L;

    public CreateUserResponse()
    {

    }

    public String getSceResponseMsg() {
        return sceResponseMsg;
    }

    public void setSceResponseMsg(String sceResponseMsg) {
        this.sceResponseMsg = sceResponseMsg;
    }

    public int getSceResponseCode() {
        return sceResponseCode;
    }

    public void setSceResponseCode(int sceResponseCode) {
        this.sceResponseCode = sceResponseCode;
    }
}
