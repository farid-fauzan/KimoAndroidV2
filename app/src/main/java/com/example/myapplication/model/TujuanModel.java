package com.example.myapplication.model;

public class TujuanModel {
    private String lokasi;
    private String harga;

    public TujuanModel(String lokasi, String harga) {
        this.lokasi = lokasi;
        this.harga = harga;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}

