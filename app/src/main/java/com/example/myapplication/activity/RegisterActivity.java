package com.example.myapplication.activity;

//import static com.example.project_ratih_java.activity.LoginActivity.md5Java;
//import static com.example.project_ratih_java.konfigurasi.api_register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.common.Priority;
//import com.androidnetworking.error.ANError;
//import com.androidnetworking.interfaces.JSONObjectRequestListener;
//import com.example.project_ratih_java.R;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Request.LoginRequest;
import com.example.myapplication.Request.RegisterRequest;
import com.example.myapplication.Services.ApiService;
import com.example.myapplication.util.ParameterLoader;
import com.example.myapplication.util.ResponseDataHandler;
import com.example.myapplication.util.TokenManager;
import com.example.myapplication.util.UserManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
//
    ParameterLoader parameterLoader;
    EditText et_email,et_password, et_confpassword;
    Button btn_reg, btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        parameterLoader = new ParameterLoader();

        et_email = findViewById(R.id.email_input);
        et_confpassword  = findViewById(R.id.repassword_input);
        et_password = findViewById(R.id.password_input);
        btn_back = findViewById(R.id.btn_back);
        btn_reg = findViewById(R.id.btn_reg);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password, confirmpassword, email;

                email = et_email.getText().toString();
                confirmpassword = et_confpassword.getText().toString();
                password =  et_password.getText().toString();

                if (password.equals(confirmpassword)){
                    if (email.equals("") || password.equals("") || confirmpassword.equals("")){
                        Toast.makeText(getApplicationContext(),"Data Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    }else {

                        RegisterRequest registerRequest = new RegisterRequest();
                        registerRequest.setRole("1");
                        registerRequest.setEmail(et_email.getText().toString());
                        registerRequest.setPassword(et_password.getText().toString());
                        registerRequest.setPasswordConfirm(et_confpassword.getText().toString());

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(parameterLoader.URL())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiService apiService = retrofit.create(ApiService.class);

                        Call<Void> call = apiService.register(registerRequest);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println("response reg : "+response);
                                if (response.isSuccessful()) {
                                    // Berhasil menyimpan data
                                    Toast.makeText(getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();

                                    LoginRequest request = new LoginRequest();
                                    request.setEmail(et_email.getText().toString());
                                    request.setPassword(et_password.getText().toString());

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(parameterLoader.URL())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    ApiService apiService = retrofit.create(ApiService.class);

                                    Call<ResponseDataHandler> callResponse = apiService.getUserLogin(request);
                                    callResponse.enqueue(new Callback<ResponseDataHandler>() {
                                        @Override
                                        public void onResponse(Call<ResponseDataHandler> callResponse, Response<ResponseDataHandler> response) {
                                            if (response.isSuccessful()) {
                                                ResponseDataHandler responseHandler = response.body();
                                                JsonElement responseData = responseHandler.getResponseData();

                                                if (responseHandler.isStatus()) { // Memeriksa status respons
                                                    String token = responseHandler.getResponseData().getAsString(); // Ambil token dari objek JSON

                                                    // Set token
                                                    TokenManager.getInstance().setToken("Bearer " + token);

                                                    Retrofit retrofit = new Retrofit.Builder()
                                                            .baseUrl(parameterLoader.URL())
                                                            .addConverterFactory(GsonConverterFactory.create())
                                                            .build();

                                                    ApiService apiService = retrofit.create(ApiService.class);

                                                    Call<ResponseDataHandler> call = apiService.getDataUser(token);
                                                    call.enqueue(new Callback<ResponseDataHandler>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseDataHandler> call, Response<ResponseDataHandler> response) {
                                                            if (response.isSuccessful()) {
                                                                ResponseDataHandler responseHandler = response.body();
                                                                JsonElement responseData = responseHandler.getResponseData();

                                                                if (responseHandler.isStatus()) { // Memeriksa status respons
                                                                    JsonObject dataObject = responseData.getAsJsonObject();
                                                                    // Set User
                                                                    System.out.println("object : "+dataObject);
                                                                    UserManager.getInstance().setIdUser(Long.valueOf(dataObject.get("idUser").getAsLong()));
                                                                    System.out.println("emaaaail : "+dataObject.get("email").getAsString());
                                                                    UserManager.getInstance().setEmail(String.valueOf(dataObject.get("email").getAsString()));
//                                                                    UserManager.getInstance().setPhone(String.valueOf(dataObject.get("phone").getAsString()));
//                                                                    UserManager.getInstance().setName(String.valueOf(dataObject.get("name").getAsString()));

                                                                }
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(Call<ResponseDataHandler> call, Throwable t) {
                                                            Toast.makeText(getApplicationContext(), "Koneksi Error!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });


                                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                    RegisterActivity.this.startActivity(intent);
                                                    finish();

                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Email atau password salah", Toast.LENGTH_SHORT).show();
                                                System.out.println("Email / Password Salah!" + response.message());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseDataHandler> call, Throwable t) {
                                            System.out.println("Gagal melakukan panggilan: " + t.getMessage());
                                            Toast.makeText(getApplicationContext(), "Koneksi Error!", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                } else {
                                    // Tangani jika respons tidak berhasil
                                    Toast.makeText(getApplicationContext(), "Register Tidak Berhasil", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                // Tangani jika permintaan gagal
                                System.out.println("Kesalahan "+t);
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Password Tidak Sesuai" ,Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}