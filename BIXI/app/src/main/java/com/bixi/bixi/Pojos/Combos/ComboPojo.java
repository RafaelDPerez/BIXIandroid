package com.bixi.bixi.Pojos.Combos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Johnny Gil Mejia on 4/9/17.
 */

public class ComboPojo {

    @SerializedName("type_commerce")
    @Expose
    private String typeCommerce;
    @SerializedName("type_commerce_id")
    @Expose
    private String typeCommerceId;

    public String getTypeCommerce() {
        return typeCommerce;
    }

    public void setTypeCommerce(String typeCommerce) {
        this.typeCommerce = typeCommerce;
    }

    public String getTypeCommerceId() {
        return typeCommerceId;
    }

    public void setTypeCommerceId(String typeCommerceId) {
        this.typeCommerceId = typeCommerceId;
    }


    @Override
    public String toString() {
        return getTypeCommerce();
    }
}

