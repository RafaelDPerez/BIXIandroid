package com.bixi.bixi.Pojos;

import java.io.Serializable;

/**
 * Created by telynet on 1/22/2017.
 */

public class Result implements Serializable {

    private final static long serialVersionUID = 4007865184775523488L;
    private String user_id;
    private String first_name;
    private String last_name;
    private String document_id;
    private String birth_date;
    private String phone1;
    private String phone2;
    private String gender;
    private String email;
    private String token;
    private String country;
    private String address;
    private String image;
    private String activate_code_expiration;
    private String balance_points;
    private String lat;
    private String lon;
    private String cdate;
    private String mdate;
    private String status;

    public String getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActivate_code_expiration() {
        return activate_code_expiration;
    }

    public void setActivate_code_expiration(String activate_code_expiration) {
        this.activate_code_expiration = activate_code_expiration;
    }

    public String getBalance_points() {
        return balance_points;
    }

    public void setBalance_points(String balance_points) {
        this.balance_points = balance_points;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
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
}
