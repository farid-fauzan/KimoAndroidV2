package com.example.myapplication.model;

public class OrderModel {
    private String tujuan;
    private String tanggal;
    private String jam;
    private String agent;
    private String status;

    public OrderModel(String tujuan, String tanggal, String jam, String agent, String status) {
        this.tujuan = tujuan;
        this.tanggal = tanggal;
        this.jam = jam;
        this.agent = agent;
        this.status = status;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
