package com.bixi.bixi.Pojos.ObjSearchProducts;

/**
 * Created by Johnny Gil Mejia on 3/7/17.
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Serializable
{

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("date_expires")
    @Expose
    private String dateExpires;
    @SerializedName("is_offer")
    @Expose
    private String isOffer;
    @SerializedName("cdate")
    @Expose
    private String cdate;
    @SerializedName("mdate")
    @Expose
    private String mdate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_favorite")
    @Expose
    private String is_favorite;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    private final static long serialVersionUID = -3537942964889807896L;

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

    public void setIsFavorite(String isFavorite)
    {
        this.is_favorite = isFavorite;
    }

    public boolean getIsFavorite(){return is_favorite.equals("1");}

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
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

}