package com.bixi.bixi.Pojos.ObjSearchProducts;

/**
 * Created by Johnny Gil Mejia on 3/7/17.
 */

import java.io.Serializable;
import java.util.List;

import com.bixi.bixi.Pojos.ObjSearchProducts.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultProductsJson implements Serializable
{

    @SerializedName("commerce_name")
    @Expose
    private String commerceName;
    @SerializedName("type_commerce_id")
    @Expose
    private String typeCommerceId;
    @SerializedName("type_commerce")
    @Expose
    private String typeCommerce;
    @SerializedName("commerce_address")
    @Expose
    private String commerceAddress;
    @SerializedName("commerce_lat")
    @Expose
    private String commerceLat;
    @SerializedName("commerce_lng")
    @Expose
    private String commerceLng;
    @SerializedName("commerce_id")
    @Expose
    private String commerceId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("products")
    @Expose
    private List<List<Product>> products = null;
    private final static long serialVersionUID = -8675013932056170891L;
    private int oferDisplay;
    private int maxquantityOffers;

    public String getCommerceName() {
        return commerceName;
    }

    public void setCommerceName(String commerceName) {
        this.commerceName = commerceName;
    }

    public String getTypeCommerceId() {
        return typeCommerceId;
    }

    public void setTypeCommerceId(String typeCommerceId) {
        this.typeCommerceId = typeCommerceId;
    }

    public String getTypeCommerce() {
        return typeCommerce;
    }

    public void setTypeCommerce(String typeCommerce) {
        this.typeCommerce = typeCommerce;
    }

    public String getCommerceAddress() {
        return commerceAddress;
    }

    public void setCommerceAddress(String commerceAddress) {
        this.commerceAddress = commerceAddress;
    }

    public String getCommerceLat() {
        return commerceLat;
    }

    public void setCommerceLat(String commerceLat) {
        this.commerceLat = commerceLat;
    }

    public String getCommerceLng() {
        return commerceLng;
    }

    public void setCommerceLng(String commerceLng) {
        this.commerceLng = commerceLng;
    }

    public String getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(String commerceId) {
        this.commerceId = commerceId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<List<Product>> getProducts() {
        return products;
    }

    public void setProducts(List<List<Product>> products) {
        this.products = products;
    }

    public int getOferDisplay() {
        return oferDisplay;
    }

    public void setOferDisplay(int oferDisplay) {
        this.oferDisplay = oferDisplay;
    }

    public int getMaxquantityOffers() {
        return maxquantityOffers;
    }

    public void setMaxquantityOffers(int maxquantityOffers) {
        this.maxquantityOffers = maxquantityOffers;
    }
}
