package com.bixi.bixi.Pojos.Combos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Johnny Gil Mejia on 4/9/17.
 */

public class ComboGeneralPojo {

        @SerializedName("sceResponseCode")
        @Expose
        private Integer sceResponseCode;
        @SerializedName("sceResponseMsg")
        @Expose
        private String sceResponseMsg;
        @SerializedName("result")
        @Expose
        private List<ComboPojo> comboPojo = null;

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

    public List<ComboPojo> getComboPojo() {
        return comboPojo ;
    }

    public void setComboPojo(List<ComboPojo> comboPojo ) {
        this.comboPojo  = comboPojo ;
    }
}
