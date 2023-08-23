package com.example.myapplication.Request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LoginRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String isPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
