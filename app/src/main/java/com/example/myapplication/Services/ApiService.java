package com.example.myapplication.Services;

import com.example.myapplication.Request.LoginRequest;
import com.example.myapplication.Request.PesananRequest;
import com.example.myapplication.Request.RegisterRequest;
import com.example.myapplication.Request.UserUpdateRequest;
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

    @POST("/user/register") // Ganti dengan endpoint API Anda
    Call<Void> register(@Body RegisterRequest body);

    @POST("/pemesanan/add-pesanan") // Ganti dengan endpoint API Anda
    Call<Void> savePemesanan(@Header("Authorization") String authorization, @Body PesananRequest body);

    @GET("/pemesanan/list-pesanan-customer")
    Call<ResponseDataHandler> getListPesanan(@Header("Authorization") String authorization, @Query("idCustomer") Long idCustomer);

    @GET("/biaya/list-tujuan")
    Call<ResponseDataHandler> getListBiaya(@Header("Authorization") String authorization, @Query("kotaAsal") String kotaAsal, @Query("layanan") String layanan);

    @GET("/pemesanan/detail-pesanan-customer")
    Call<ResponseDataHandler> getDetailPesanan(@Header("Authorization") String authorization, @Query("idCustomer") Long idCustomer, @Query("idPemesanan") Long idPemesanan);

    @POST("/user/update-user")
    Call<Void> updateUser(@Body UserUpdateRequest body);

}
