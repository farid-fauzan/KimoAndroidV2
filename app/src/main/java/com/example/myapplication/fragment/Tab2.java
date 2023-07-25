package com.example.myapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.adapter.TujuanAdapter;
import com.example.myapplication.model.OrderModel;
import com.example.myapplication.model.TujuanModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

import java.util.ArrayList;


public class Tab2 extends Fragment implements OnMapReadyCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;
    private GoogleMap googleMap;
    private DatabaseReference locationRef;

    ListView listViewOrder;
    RelativeLayout relativeLayoutListPesanan, relativeLayoutInvoice, relativeLayoutMap;
    Button btn_map_invoice, btn_back_invoice;


    public View onCreateView(@NonNull LayoutInflater inflater,
                                                     final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        relativeLayoutListPesanan = view.findViewById(R.id.relative_list_pesanan);
        relativeLayoutInvoice = view.findViewById(R.id.relative_invoice);
        relativeLayoutMap = view.findViewById(R.id.relative_map);

        listViewOrder = view.findViewById(R.id.list_view_order);

        btn_back_invoice = view.findViewById(R.id.btn_back_invoice);
        btn_map_invoice = view.findViewById(R.id.btn_next_invoice);

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
        ArrayList<OrderModel> orderModels = new ArrayList<>();
        orderModels.add(new OrderModel("Bandung", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Payment Pending"));
        orderModels.add(new OrderModel("Padang", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Payment Pending"));
        orderModels.add(new OrderModel("Bali", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Payment Pending"));
        orderModels.add(new OrderModel("Bengkulu", "2 Mey, 2023", "10:00 am", "Faisal Kimo", "Pengiriman"));

        OrderAdapter adapter = new OrderAdapter(getActivity(), orderModels);

        listViewOrder.setAdapter(adapter);

        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (orderModels.get(position).getStatus().equals("Paid")){
//                    btn_map_invoice.setVisibility(View.VISIBLE);
//                }
                relativeLayoutListPesanan.setVisibility(View.GONE);
                relativeLayoutInvoice.setVisibility(View.VISIBLE);
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
