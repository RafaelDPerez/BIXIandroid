package com.bixi.bixi.Pojos;

import java.io.Serializable;

/**
 * Created by telynet on 1/22/2017.
 */

public class UserLogin implements Serializable {


    private final static long serialVersionUID = 4007865184775523488L;
    private String sceResponseCode;
    private String sceResponseMsg;
    private String sceToken;
    private Result result;

    public String getSceResponseCode() {
        return sceResponseCode;
    }

    public void setSceResponseCode(String sceResponseCode) {
        this.sceResponseCode = sceResponseCode;
    }

    public String getSceResponseMsg() {
        return sceResponseMsg;
    }

    public void setSceResponseMsg(String sceResponseMsg) {
        this.sceResponseMsg = sceResponseMsg;
    }

    public String getSceToken() {
        return sceToken;
    }

    public void setSceToken(String sceToken) {
        this.sceToken = sceToken;
    }
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
