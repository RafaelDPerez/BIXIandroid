package com.bixi.bixi.Pojos;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class OffersPointsClaim {

    private String pin;
    private String product_id;
    private String description;
    private int points;
    private String qr;

    public OffersPointsClaim() {
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
}
