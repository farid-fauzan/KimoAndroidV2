package com.example.myapplication.Services;

import com.example.myapplication.Request.LoginRequest;
import com.example.myapplication.Request.PesananRequest;
import com.example.myapplication.util.ResponseDataHandler;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/token/extract")
    Call<ResponseDataHandler> getDataUser(@Query("token") String token);

    @POST("/user/login")
    Call<ResponseDataHandler> getUserLogin(@Body LoginRequest body);

    @POST("/pemesanan/add-pesanan") // Ganti dengan endpoint API Anda
    Call<Void> savePemesanan(@Header("Authorization") String authorization, @Body PesananRequest body);

    @GET("/pemesanan/list-pesanan-customer")
    Call<ResponseDataHandler> getListPesanan(@Header("Authorization") String authorization, @Query("idCustomer") Long idCustomer);

}
