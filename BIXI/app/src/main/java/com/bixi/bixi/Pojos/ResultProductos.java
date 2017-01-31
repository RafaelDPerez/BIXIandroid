package com.bixi.bixi.Pojos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by telynet on 1/26/2017.
 */

public class ResultProductos implements Serializable
{

    private String commerceName;
    private String typeCommerceId;
    private String typeCommerce;
    private String commerceAddress;
    private String commerceLat;
    private String commerceLng;
    private String commerceId;
    private String productId;
    private String name;
    private String description;
    private String points;
    private String quantity;
    private String dateExpires;
    private String isOffer;
    private String status;
    private List<String> images = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1989159632270142560L;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(String dateExpires) {
        this.dateExpires = dateExpires;
    }

    public String getIsOffer() {
        return isOffer;
    }

    public void setIsOffer(String isOffer) {
        this.isOffer = isOffer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}