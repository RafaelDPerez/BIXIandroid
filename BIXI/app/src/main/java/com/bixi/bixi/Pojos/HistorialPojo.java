package com.bixi.bixi.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class HistorialPojo implements Serializable
{

    @SerializedName("sceResponseCode")
    @Expose
    private Integer sceResponseCode;
    @SerializedName("sceResponseMsg")
    @Expose
    private String sceResponseMsg;
    @SerializedName("result")
    @Expose
    private List<ResultHistoricalPojo> result = null;
    private final static long serialVersionUID = 4739601907966997784L;

    public Integer getSceResponseCode() {
        return sceResponseCode;
    }

    public void setSceResponseCode(Integer sceResponseCode) {
        this.sceResponseCode = sceResponseCode;
    }

    public String getSceResponseMsg() {
        return sceResponseMsg;
    }

    public void setSceResponseMsg(String sceResponseMsg) {
        this.sceResponseMsg = sceResponseMsg;
    }

    public List<ResultHistoricalPojo> getResult() {
        return result;
    }

    public void setResult(List<ResultHistoricalPojo> result) {
        this.result = result;
    }

}
