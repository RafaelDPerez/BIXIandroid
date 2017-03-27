package com.bixi.bixi.Pojos.ObjSearchProducts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public class ProductsLiketItJson {

    @SerializedName("sceResponseCode")
    @Expose
    private Integer sceResponseCode;
    @SerializedName("sceResponseMsg")
    @Expose
    private String sceResponseMsg;
    @SerializedName("result")
    @Expose
    private List<ResultProductsLikerItJson> result = null;

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

    public List<ResultProductsLikerItJson> getResult() {
        return result;
    }

    public void setResult(List<ResultProductsLikerItJson> result) {
        this.result = result;
    }

}

