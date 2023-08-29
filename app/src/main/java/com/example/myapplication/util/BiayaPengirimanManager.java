package com.example.myapplication.util;

public class BiayaPengirimanManager {
    private static BiayaPengirimanManager instance;
    private String kotaAsal;
    private String layanan;

    private BiayaPengirimanManager() {}

    public static synchronized BiayaPengirimanManager getInstance() {
        if (instance == null) {
            instance = new BiayaPengirimanManager();
        }
        return instance;
    }

    public String getKotaAsal() {
        return kotaAsal;
    }

    public void setKotaAsal(String kotaAsal) {
        this.kotaAsal = kotaAsal;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }
}
