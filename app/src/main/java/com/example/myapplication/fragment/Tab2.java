package com.example.myapplication.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Response.ListPesananResponse;
import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.util.ResponseDataHandler;
import com.example.myapplication.Services.ApiService;
import com.example.myapplication.util.ParameterLoader;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.model.OrderModel;
import com.example.myapplication.util.TokenManager;
import com.example.myapplication.util.UserManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tab2 extends Fragment implements OnMapReadyCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;
    private ParameterLoader parameterLoader;
    private GoogleMap googleMap;
    private DatabaseReference locationRef;

    ListView listViewOrder;
    RelativeLayout relativeLayoutListPesanan, relativeLayoutInvoice, relativeLayoutMap;
    Button btn_map_invoice, btn_back_invoice;

    TextView textViewKodeBooking, textViewTanggalPemesananInvoice, textViewJamPemesananInvoice,
    textViewZonaWaktuInvoice, textViewTujuanInvoice, textViewLocationInvoice, textViewExtraCcInvoice, textViewLayananPengirimanInvoice,
    textViewNamaCustomerInvoice, textViewTeleponCustomerInvoice, textViewEmailCustomerInvoice,
    textViewHargaTotalInvoice;


    public View onCreateView(@NonNull LayoutInflater inflater,
                                                     final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        parameterLoader = new ParameterLoader();

        relativeLayoutListPesanan = view.findViewById(R.id.relative_list_pesanan);
        relativeLayoutInvoice = view.findViewById(R.id.relative_invoice);
        relativeLayoutMap = view.findViewById(R.id.relative_map);

        listViewOrder = view.findViewById(R.id.list_view_order);

        btn_back_invoice = view.findViewById(R.id.btn_back_invoice);
        btn_map_invoice = view.findViewById(R.id.btn_next_invoice);

        //detail invoice
        textViewKodeBooking = view.findViewById(R.id.text_view_kode_booking);
        textViewTanggalPemesananInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_tanggal_verifikasi);
        textViewJamPemesananInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_jam_verifikasi);
        textViewZonaWaktuInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_zona_waktu_verifikasi);
        textViewTujuanInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_tujuan_verifikasi);
        textViewLocationInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_lokasi_verifikasi);
        textViewExtraCcInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_extra_cc_verifikasi);
        textViewLayananPengirimanInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_layanan_verifikasi);
        textViewNamaCustomerInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_nama_customer_verifikasi);
        textViewTeleponCustomerInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_telepon_customer_verifikasi);
        textViewEmailCustomerInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_email_customer_verifikasi);
        textViewHargaTotalInvoice = view.findViewById(R.id.include_invoice).findViewById(R.id.text_view_detail_harga_total_verifikasi);


//        if (!(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//        }

        FirebaseApp.initializeApp(getContext());
        locationRef = FirebaseDatabase.getInstance().getReference("location");

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        relativeLayoutListPesanan.setVisibility(View.VISIBLE);
        relativeLayoutInvoice.setVisibility(View.GONE);
        relativeLayoutMap.setVisibility(View.GONE);

//        btn_map_invoice.setVisibility(View.GONE);

        order();
        map();

        return view;
    }

    private void map() {

    }


    private void order() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(parameterLoader.URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        String authorizationHeader = TokenManager.getInstance().getToken();

        Long customerId = (Long) UserManager.getInstance().getIdUser(); // Ganti dengan ID customer yang sesuai
        Call<ResponseDataHandler> call = apiService.getListPesanan(authorizationHeader, customerId);
        call.enqueue(new Callback<ResponseDataHandler>() {
            @Override
            public void onResponse(Call<ResponseDataHandler> call, Response<ResponseDataHandler> response) {
                if (response.isSuccessful()) {
                    ResponseDataHandler responseHandler = response.body();
                    JsonElement responseData = responseHandler.getResponseData();

                    if (responseHandler.isStatus()) { // Memeriksa status respons
                        if (responseData.isJsonArray()) { // Memeriksa apakah data adalah array JSON
                            JsonArray dataArray = responseData.getAsJsonArray();
                            Type listType = new TypeToken<List<ListPesananResponse>>() {}.getType();
                            List<ListPesananResponse> pesananList = new Gson().fromJson(dataArray, listType);

                            ArrayList<OrderModel> orderModels = new ArrayList<>();
//                            orderModels.add(new OrderModel("Bandung", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Payment Pending"));
                            //        orderModels.add(new OrderModel("Padang", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Payment Pending"));
                            //        orderModels.add(new OrderModel("Bali", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Payment Pending"));
                            //        orderModels.add(new OrderModel("Bengkulu", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Pengiriman"));
                            listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (orderModels.get(position).getStatus().equals("Paid")){
//                    btn_map_invoice.setVisibility(View.VISIBLE);
//                }
                                    Long idPemesanan = orderModels.get(position).getIdPesanan();

                                    textViewKodeBooking.setText("SYSTEM");
                                    textViewTanggalPemesananInvoice.setText("SYSTEM");
                                    textViewJamPemesananInvoice.setText("SYSTEM");
                                    textViewZonaWaktuInvoice.setText("SYSTEM");
                                    textViewTujuanInvoice.setText("SYSTEM");
                                    textViewLocationInvoice.setText("SYSTEM");
                                    textViewExtraCcInvoice.setText("SYSTEM");
                                    textViewLayananPengirimanInvoice.setText("SYSTEM");
                                    textViewNamaCustomerInvoice.setText("SYSTEM");
                                    textViewTeleponCustomerInvoice.setText("SYSTEM");
                                    textViewEmailCustomerInvoice.setText("SYSTEM");
                                    textViewHargaTotalInvoice.setText("SYSTEM");

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(parameterLoader.URL())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    ApiService apiService = retrofit.create(ApiService.class);

                                    Call<ResponseDataHandler> call = apiService.getDetailPesanan(authorizationHeader, customerId, idPemesanan);
                                    call.enqueue(new Callback<ResponseDataHandler>() {
                                        @Override
                                        public void onResponse(Call<ResponseDataHandler> call, Response<ResponseDataHandler> response) {
                                            System.out.println("response : "+response);
                                            if (response.isSuccessful()) {
                                                ResponseDataHandler responseHandler = response.body();
                                                JsonElement responseData = responseHandler.getResponseData();

                                                if (responseHandler.isStatus()) { // Memeriksa status respons
                                                    JsonObject dataObject = responseData.getAsJsonObject();
                                                    textViewKodeBooking.setText(dataObject.getAsJsonPrimitive("kodeBooking").getAsString());

                                                    String tglString = dataObject.getAsJsonPrimitive("tglPemesanan").getAsString();
                                                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(tglString);
                                                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);
                                                    String formattedDate = zonedDateTime.format(outputFormatter);
                                                    String month = zonedDateTime.getMonth().toString().substring(0, 3).toLowerCase();
                                                    formattedDate = formattedDate.replace("mmm", month);

                                                    textViewTanggalPemesananInvoice.setText(formattedDate);

                                                    // Mengambil jam dan menit dari ZonedDateTime
                                                    int hour = zonedDateTime.getHour();
                                                    int minute = zonedDateTime.getMinute();
                                                    String formattedTime = String.format("%02d:%02d", hour, minute);

                                                    textViewJamPemesananInvoice.setText(formattedTime);

                                                    ZoneId zoneId = zonedDateTime.getZone();

                                                    textViewZonaWaktuInvoice.setText(zoneId.toString());
                                                    textViewTujuanInvoice.setText(dataObject.getAsJsonPrimitive("lokasiTujuan").getAsString());
                                                    textViewLocationInvoice.setText(dataObject.getAsJsonPrimitive("lokasiJemput").getAsString());

                                                    boolean extraCcBool =  dataObject.getAsJsonPrimitive("extraCc").getAsBoolean();
                                                    if (extraCcBool == false){
                                                        textViewExtraCcInvoice.setText("");
                                                    }else {
                                                        textViewExtraCcInvoice.setText("2000cc");
                                                    }
                                                    textViewLayananPengirimanInvoice.setText(dataObject.getAsJsonPrimitive("layananPengiriman").getAsString());
                                                    textViewNamaCustomerInvoice.setText(UserManager.getInstance().getName());
                                                    textViewTeleponCustomerInvoice.setText(UserManager.getInstance().getPhone());
                                                    textViewEmailCustomerInvoice.setText(UserManager.getInstance().getEmail());
                                                    textViewHargaTotalInvoice.setText(dataObject.getAsJsonPrimitive("hargaTotal").getAsString());

                                                    String status = dataObject.getAsJsonPrimitive("status").getAsString();
                                                    System.out.println("stat : "+status);

                                                    btn_map_invoice.setVisibility(View.GONE);
                                                    if (status.equals("Paid") || status.equals("Perjalanan")){
                                                        btn_map_invoice.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            }else {
                                                Toast.makeText(getContext(), "User/Pesanan Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
                                                System.out.println("User/Pesanan Tidak Ditemukan!" + response.message());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseDataHandler> call, Throwable t) {
                                            System.out.println("Gagal melakukan panggilan: " + t.getMessage());
                                            Toast.makeText(getContext(), "Koneksi Error!", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                    relativeLayoutListPesanan.setVisibility(View.GONE);
                                    relativeLayoutInvoice.setVisibility(View.VISIBLE);
                                }
                            });

                            if (pesananList != null && pesananList.size() > 0) {
                                for (ListPesananResponse pesananResponse : pesananList) {
                                    String status = ParameterLoader.STATUS_PESANAN.PROSES;
                                    orderModels.add(new OrderModel(pesananResponse.getLokasiTujuan(), "1 Jan, 0000", "00:00 am",
                                            "System", pesananResponse.getStatus(), Long.valueOf(pesananResponse.getIdPemesanan())));
                                    // Lanjutkan untuk atribut lainnya

                                    OrderAdapter adapter = new OrderAdapter(getActivity(), orderModels);

                                    listViewOrder.setAdapter(adapter);
                                }
                            } else {
                                System.out.println("Tidak ada data pesanan.");
                            }
                        } else {
                            System.out.println("Data tidak valid.");
                        }
                    } else {
                        System.out.println("Respons tidak berhasil: " + responseHandler.getMessage());
                    }
                } else {
                    System.out.println("Respons tidak berhasil: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseDataHandler> call, Throwable t) {
                System.out.println("Gagal melakukan panggilan: " + t.getMessage());
            }
        });


        btn_back_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutInvoice.setVisibility(View.GONE);
                relativeLayoutListPesanan.setVisibility(View.VISIBLE);
            }
        });

        btn_map_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutInvoice.setVisibility(View.GONE);
                relativeLayoutMap.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Do something when map is clicked
            }
        });

        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                    double longitude = dataSnapshot.child("longitude").getValue(Double.class);
                    LatLng location = new LatLng(latitude, longitude);
                    googleMap.clear();

                    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.mobil);
                    Bitmap smallIcon = Bitmap.createScaledBitmap(icon, 120, 120, false);
                    googleMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromBitmap(smallIcon)));

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to read location: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
