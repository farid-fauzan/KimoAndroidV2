package com.example.myapplication.Response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ListPesananResponse {
    @SerializedName("idPemesanan")
    private int idPemesanan;

    @SerializedName("biayaLayanan")
    private String biayaLayanan;

    @SerializedName("extraCc")
    private boolean extraCc;

    @SerializedName("hargaTotal")
    private String hargaTotal;

    @SerializedName("idCustomer")
    private int idCustomer;

    @SerializedName("idDriver")
    private int idDriver;

    @SerializedName("layananPengiriman")
    private String layananPengiriman;

    @SerializedName("lokasiJemput")
    private String lokasiJemput;

    @SerializedName("lokasiTujuan")
    private String lokasiTujuan;

    @SerializedName("kodeBooking")
    private String kodeBooking;

    @SerializedName("status")
    private String status;

    @SerializedName("tglPemesanan")
    private Date tglPemesanan;

    // Constructor, getters, setters, dan lain-lain


    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getBiayaLayanan() {
        return biayaLayanan;
    }

    public void setBiayaLayanan(String biayaLayanan) {
        this.biayaLayanan = biayaLayanan;
    }

    public boolean isExtraCc() {
        return extraCc;
    }

    public void setExtraCc(boolean extraCc) {
        this.extraCc = extraCc;
    }

    public String getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(String hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public String getLayananPengiriman() {
        return layananPengiriman;
    }

    public void setLayananPengiriman(String layananPengiriman) {
        this.layananPengiriman = layananPengiriman;
    }

    public String getLokasiJemput() {
        return lokasiJemput;
    }

    public void setLokasiJemput(String lokasiJemput) {
        this.lokasiJemput = lokasiJemput;
    }

    public String getLokasiTujuan() {
        return lokasiTujuan;
    }

    public void setLokasiTujuan(String lokasiTujuan) {
        this.lokasiTujuan = lokasiTujuan;
    }

    public String getKodeBooking() {
        return kodeBooking;
    }

    public void setKodeBooking(String kodeBooking) {
        this.kodeBooking = kodeBooking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTglPemesanan() {
        return tglPemesanan;
    }

    public void setTglPemesanan(Date tglPemesanan) {
        this.tglPemesanan = tglPemesanan;
    }
}
