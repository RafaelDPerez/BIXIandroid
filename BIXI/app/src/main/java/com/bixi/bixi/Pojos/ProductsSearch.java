package com.bixi.bixi.Pojos;

import android.net.wifi.p2p.WifiP2pManager;

import java.io.Serializable;

/**
 * Created by telynet on 1/26/2017.
 */

public class ProductsSearch implements Serializable {

    private final static long serialVersionUID = 4007865184775523488L;
    private  int type_commerce_id;
    private String search;
    private String order_by;
    private String is_ofer;
    private String start;
    private String distance;
    private float lat;
    private float lng;

    public ProductsSearch()
    {

    }


    public int getType_commerce_id() {
        return type_commerce_id;
    }

    public void setType_commerce_id(int type_commerce_id) {
        this.type_commerce_id = type_commerce_id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public String getIs_ofer() {
        return is_ofer;
    }

    public void setIs_ofer(String is_ofer) {
        this.is_ofer = is_ofer;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
