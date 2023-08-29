package com.example.myapplication.Response;

import com.google.gson.annotations.SerializedName;

public class ListBiayaResponse {

    @SerializedName("idBiaya")
    private Long idBiaya;

    @SerializedName("kotaAsal")
    private String kotaAsal;

    @SerializedName("kotaTujuan")
    private String kotaTujuan;

    @SerializedName("harga")
    private Double harga;

    public Long getIdBiaya() {
        return idBiaya;
    }

    public String getKotaAsal() {
        return kotaAsal;
    }

    public String getKotaTujuan() {
        return kotaTujuan;
    }

    public Double getHarga() {
        return harga;
    }
}
