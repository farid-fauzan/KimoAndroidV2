package com.example.myapplication.Services;

import com.example.myapplication.Request.PesananRequest;
import com.example.myapplication.Response.ResponseDataHandler;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/pemesanan/add-pesanan") // Ganti dengan endpoint API Anda
    Call<Void> savePemesanan(@Body PesananRequest body);

    @GET("/pemesanan/list-pesanan-customer")
    Call<ResponseDataHandler> getListPesanan(@Query("idCustomer") Long idCustomer);
}
