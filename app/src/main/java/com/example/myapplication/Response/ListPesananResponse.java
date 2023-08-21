package com.example.myapplication.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPesananResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<ListPesananResponseData> pesananList;

    // Getter methods

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public List<ListPesananResponseData> getPesananList() {
        return pesananList;
    }
}
