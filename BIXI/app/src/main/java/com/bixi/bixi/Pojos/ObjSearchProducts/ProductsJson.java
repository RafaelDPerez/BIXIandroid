package com.bixi.bixi.Pojos.ObjSearchProducts;

/**
 * Created by Johnny Gil Mejia on 3/7/17.
 */

import java.io.Serializable;
import java.util.List;

import com.bixi.bixi.Pojos.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsJson implements Serializable
{

    @SerializedName("sceResponseCode")
    @Expose
    private int sceResponseCode;
    @SerializedName("sceResponseMsg")
    @Expose
    private String sceResponseMsg;
    @SerializedName("total_records")
    @Expose
    private int totalRecords;
    @SerializedName("per_page")
    @Expose
    private int perPage;
    @SerializedName("result")
    @Expose
    private List<ResultProductsJson> result = null;
    private final static long serialVersionUID = -585484825237387055L;

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

    public List<ResultProductsJson> getResult() {
        return result;
    }

    public void setResult(List<ResultProductsJson> result) {
        this.result = result;
    }

}
