package com.bixi.bixi.Pojos;

import java.io.Serializable;

/**
 * Created by telynet on 1/25/2017.
 */

public class UserSimple implements Serializable {

    private final static long serialVersionUID = 4007865184775523488L;
    private String email;
    private String password;
    private String encrypted;

    public UserSimple()
    {

    }

    public UserSimple(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }
}
