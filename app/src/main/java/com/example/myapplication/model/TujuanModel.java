package com.example.myapplication.model;

public class TujuanModel {
    private String lokasi;
    private Double harga;

    public TujuanModel(String lokasi, Double harga) {
        this.lokasi = lokasi;
        this.harga = harga;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }
}

