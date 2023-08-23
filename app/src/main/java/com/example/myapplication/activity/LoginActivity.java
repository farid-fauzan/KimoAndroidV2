package com.example.myapplication.activity;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Request.LoginRequest;
import com.example.myapplication.Request.PesananRequest;
import com.example.myapplication.Response.ListPesananResponseData;
import com.example.myapplication.Services.ApiService;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.model.OrderModel;
import com.example.myapplication.util.ParameterLoader;
import com.example.myapplication.util.ResponseDataHandler;
import com.example.myapplication.util.TokenManager;
import com.example.myapplication.util.UserManager;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {


    Button btn_login, btn_register;
    String email,password;
    EditText et_email,et_password;

    Intent intent;
    SignInButton signInButton;
    private ParameterLoader parameterLoader;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parameterLoader = new ParameterLoader();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_reg);
        et_email = findViewById(R.id.email_input);
        et_password = findViewById(R.id.password_input_login);

        signInButton = findViewById(R.id.google_login_button);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();

                if (email.isEmpty()) {
                    et_email.setError("Field ini harus diisi");
                    et_email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    et_password.setError("Field ini harus diisi");
                    et_password.requestFocus();
                    return;
                }

                if (!password.isEmpty() && !email.isEmpty()) {

                    LoginRequest request = new LoginRequest();
                    request.setEmail(email);
                    request.setPassword(password);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(parameterLoader.URL())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiService apiService = retrofit.create(ApiService.class);

                    Call<ResponseDataHandler> call = apiService.getUserLogin(request);
                    call.enqueue(new Callback<ResponseDataHandler>() {
                        @Override
                        public void onResponse(Call<ResponseDataHandler> call, Response<ResponseDataHandler> response) {
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

                                    call = apiService.getDataUser(token);
                                    call.enqueue(new Callback<ResponseDataHandler>() {
                                        @Override
                                        public void onResponse(Call<ResponseDataHandler> call, Response<ResponseDataHandler> response) {
                                            if (response.isSuccessful()) {
                                                ResponseDataHandler responseHandler = response.body();
                                                JsonElement responseData = responseHandler.getResponseData();

                                                if (responseHandler.isStatus()) { // Memeriksa status respons
                                                    JsonObject dataObject = responseData.getAsJsonObject();
                                                    // Set User
                                                    UserManager.getInstance().setIdUser(Long.valueOf(dataObject.get("idUser").getAsLong()));
                                                }
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ResponseDataHandler> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Koneksi Error!", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                    intent = new Intent(LoginActivity.this, MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
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

                }

            }


        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Izin ditolak, tampilkan pesan atau lakukan tindakan yang sesuai
                Toast.makeText(this, "Izin ditolak. Tidak dapat membuat file PDF.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


