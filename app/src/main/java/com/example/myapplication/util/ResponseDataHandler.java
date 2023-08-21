package com.example.myapplication.util;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class ResponseDataHandler {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private JsonElement responseData; // Menggunakan JsonElement

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public JsonElement getResponseData() {
        return responseData;
    }
}
