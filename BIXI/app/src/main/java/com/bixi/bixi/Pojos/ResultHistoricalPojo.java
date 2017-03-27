package com.bixi.bixi.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class ResultHistoricalPojo implements Serializable
{

    @SerializedName("user_by_full_name")
    @Expose
    private String userByFullName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("commerce_name")
    @Expose
    private String commerceName;
    @SerializedName("cdate")
    @Expose
    private String cdate;
    private final static long serialVersionUID = 1648736929009204468L;

    public String getUserByFullName() {
        return userByFullName;
    }

    public void setUserByFullName(String userByFullName) {
        this.userByFullName = userByFullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCommerceName() {
        return commerceName;
    }

    public void setCommerceName(String commerceName) {
        this.commerceName = commerceName;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

}

