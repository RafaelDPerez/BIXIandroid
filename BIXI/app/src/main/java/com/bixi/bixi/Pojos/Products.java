package com.bixi.bixi.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by telynet on 1/26/2017.
 */

public class Products implements Serializable
{

    private int sceResponseCode;
    private String sceResponseMsg;
    private int totalRecords;
    private int perPage;
    @SerializedName("result")
    private List<ResultProductos> result = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -764247576907629232L;

    public int getSceResponseCode() {
        return sceResponseCode;
    }

    public void setSceResponseCode(int sceResponseCode) {
        this.sceResponseCode = sceResponseCode;
    }

    public String getSceResponseMsg() {
        return sceResponseMsg;
    }

    public void setSceResponseMsg(String sceResponseMsg) {
        this.sceResponseMsg = sceResponseMsg;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public List<ResultProductos> getResult() {
        return result;
    }

    public void setResult(List<ResultProductos> result) {
        this.result = result;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}