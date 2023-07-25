package com.example.myapplication.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TujuanAdapter;
import com.example.myapplication.model.CustomerModel;
import com.example.myapplication.model.PemesananModel;
import com.example.myapplication.model.TujuanModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Tab1 extends Fragment {

    CardView cardViewJakarta, cardViewSurabaya,
             cardViewSelfDrive, cardViewCarCarrier, cardViewKapalRoro, cardViewTowing, cardViewContainer, cardViewTanggal, cardViewWaktu;
    RelativeLayout relativeLayoutLokasi, relativeLayoutLayanan, relativeLayoutTujuan,
            relativeLayoutTanggal, relativeLayoutCc, relativeLayoutCustomer, relativeLayoutPesanan, relativeLayoutKonfirmasi;
    Button btnBackLayanan, btnBackTujuan, btnBackTanggal, btnNextTanggal, btnBackCc,
            btnNextCc, btnBackCostumer, btnNextCostumer, btnBackPesanan, btnNextPesanan, btnNextKonfirmasi;
    ListView listViewTujuan;
    TextView textViewTanggalDipilih, textViewWaktuDipilih, textViewInfoPemesanan,
            textViewTanggalPemesanan, textViewJamPemesanan, textViewZonaWaktu, textViewTujuan,
            textViewLocation, textViewExtraCc, textViewLayananPengiriman, textViewNamaCustomer, textViewTeleponCustomer,
            textViewEmailCustomer, textViewHargaTotal,

            textViewTanggalPemesananKonfirmasi, textViewJamPemesananKonfirmasi, textViewZonaWaktuKonfirmasi, textViewTujuanKonfirmasi,
            textViewLocationKonfirmasi, textViewExtraCcKonfirmasi, textViewLayananPengirimanKonfirmasi, textViewNamaCustomerKonfirmasi, textViewTeleponCustomerKonfirmasi,
            textViewEmailCustomerKonfirmasi, textViewHargaTotalKonfirmasi, textViewKodeBooking;
    CheckBox cb_extra_cc;
    TextInputEditText editTextNamaCustomer, editTextTeleponCustomer, editTextEmail, editTextPesan;

    PemesananModel pemesananModel = new PemesananModel();
    CustomerModel customerModel = new CustomerModel();


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        cardViewJakarta = view.findViewById(R.id.card_view_jakarta);
        cardViewSurabaya = view.findViewById(R.id.card_view_surabaya);

        cardViewSelfDrive = view.findViewById(R.id.card_view_self_drive);
        cardViewCarCarrier = view.findViewById(R.id.card_view_car_carrier);
        cardViewKapalRoro = view.findViewById(R.id.card_view_kapal_roro);
        cardViewTowing = view.findViewById(R.id.card_view_towing);
        cardViewContainer = view.findViewById(R.id.card_view_container);

        cardViewTanggal = view.findViewById(R.id.card_view_tanggal);
        cardViewWaktu = view.findViewById(R.id.card_view_waktu);

        relativeLayoutLokasi = view.findViewById(R.id.relative_pilih_lokasi);
        relativeLayoutLayanan = view.findViewById(R.id.relative_pilih_layanan);
        relativeLayoutTujuan = view.findViewById(R.id.relative_pilih_tujuan);
        relativeLayoutTanggal = view.findViewById(R.id.relative_pilih_tanggal);
        relativeLayoutCc = view.findViewById(R.id.relative_pilih_cc);
        relativeLayoutCustomer = view.findViewById(R.id.relative_detail_customer);
        relativeLayoutPesanan = view.findViewById(R.id.relative_detail_pesanan);
        relativeLayoutKonfirmasi = view.findViewById(R.id.relative_konfirmasi);

        btnBackLayanan = view.findViewById(R.id.btn_back_layanan);
        btnBackTujuan = view.findViewById(R.id.btn_back_tujuan);
        btnBackTanggal = view.findViewById(R.id.btn_back_tanggal);
        btnNextTanggal = view.findViewById(R.id.btn_next_tanggal);
        btnBackCc = view.findViewById(R.id.btn_back_cc);
        btnNextCc = view.findViewById(R.id.btn_next_cc);
        btnBackCostumer = view.findViewById(R.id.btn_back_customer);
        btnNextCostumer = view.findViewById(R.id.btn_next_customer);
        btnBackPesanan = view.findViewById(R.id.btn_back_pesanan);
        btnNextPesanan = view.findViewById(R.id.btn_next_pesanan);
        btnNextKonfirmasi = view.findViewById(R.id.btn_next_konfirmasi);

        listViewTujuan = view.findViewById(R.id.list_view_tujuan);

        cb_extra_cc = view.findViewById(R.id.cb_cc);

        textViewTanggalDipilih = view.findViewById(R.id.text_view_tanggal_dipilih);
        textViewWaktuDipilih = view.findViewById(R.id.text_view_waktu_dipilih);
        textViewInfoPemesanan = view.findViewById(R.id.text_view_keterangan_info_pemesanan);

        //detail pemesanan
        textViewTanggalPemesanan = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_tanggal_verifikasi);
        textViewJamPemesanan = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_jam_verifikasi);
        textViewZonaWaktu = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_zona_waktu_verifikasi);
        textViewTujuan = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_tujuan_verifikasi);
        textViewLocation = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_lokasi_verifikasi);
        textViewExtraCc = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_extra_cc_verifikasi);
        textViewLayananPengiriman = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_layanan_verifikasi);
        textViewNamaCustomer = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_nama_customer_verifikasi);
        textViewTeleponCustomer = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_telepon_customer_verifikasi);
        textViewEmailCustomer = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_email_customer_verifikasi);
        textViewHargaTotal = view.findViewById(R.id.include_pesanan).findViewById(R.id.text_view_detail_harga_total_verifikasi);

        //detail konfirmasi
        textViewKodeBooking = view.findViewById(R.id.text_view_kode_booking);
        textViewTanggalPemesananKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_tanggal_verifikasi);
        textViewJamPemesananKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_jam_verifikasi);
        textViewZonaWaktuKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_zona_waktu_verifikasi);
        textViewTujuanKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_tujuan_verifikasi);
        textViewLocationKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_lokasi_verifikasi);
        textViewExtraCcKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_extra_cc_verifikasi);
        textViewLayananPengirimanKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_layanan_verifikasi);
        textViewNamaCustomerKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_nama_customer_verifikasi);
        textViewTeleponCustomerKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_telepon_customer_verifikasi);
        textViewEmailCustomerKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_email_customer_verifikasi);
        textViewHargaTotalKonfirmasi = view.findViewById(R.id.include_konfirmasi).findViewById(R.id.text_view_detail_harga_total_verifikasi);

        //input customer
        editTextNamaCustomer = view.findViewById(R.id.input_text_customer_name);
        editTextTeleponCustomer = view.findViewById(R.id.input_text_customer_phone);
        editTextEmail = view.findViewById(R.id.input_text_customer_email);
        editTextPesan = view.findViewById(R.id.input_text_customer_message);

        //layout sesuai urutan
        relativeLayoutLokasi.setVisibility(View.VISIBLE);
        relativeLayoutLayanan.setVisibility(View.GONE);
        relativeLayoutTujuan.setVisibility(View.GONE);
        relativeLayoutTanggal.setVisibility(View.GONE);
        relativeLayoutCc.setVisibility(View.GONE);
        relativeLayoutCustomer.setVisibility(View.GONE);
        relativeLayoutPesanan.setVisibility(View.GONE);
        relativeLayoutKonfirmasi.setVisibility(View.GONE);



        lokasi();
        layanan();
        tujuan();
        tanggal();
        cc();
        costumer();
        pesanan();
        konfirmasi();

        return view;
    }

    private void konfirmasi() {
// Temukan tombol dalam layout dan tambahkan OnClickListener kepadanya
        btnNextKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Cek apakah aplikasi memiliki izin untuk menulis ke penyimpanan eksternal
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Jika izin belum diberikan, tampilkan dialog permintaan izin
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    System.out.println("1");
                } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    System.out.println("2");
                }  else {
                    // Jika izin sudah diberikan, buat file PDF
                    System.out.println("4");
                    createPdf();
                }

            }
        });

    }

    private void pesanan() {
        btnBackPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutPesanan.setVisibility(View.GONE);
                relativeLayoutCustomer.setVisibility(View.VISIBLE);
            }
        });

        btnNextPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ISI TANGGAL  + "+ textViewTanggalPemesanan.getText().toString());

                String tanggalPemesanan =  textViewTanggalPemesanan.getText().toString();
                String jamPemesanan = textViewJamPemesanan.getText().toString();
                String zonaWaktu = textViewZonaWaktu.getText().toString();
                String tujuan = textViewTujuan.getText().toString();
                String location = textViewLocation.getText().toString();
                String extra = textViewExtraCc.getText().toString();
                String layanan = textViewLayananPengiriman.getText().toString();
                String namaCustomer = textViewNamaCustomer.getText().toString();
                String teleponCustomer = textViewTeleponCustomer.getText().toString();
                String emailCustomer = textViewEmailCustomer.getText().toString();
                String hargaTotal = textViewHargaTotal.getText().toString();

                //kode booking untuk layout konfirmasi
                String kodeBooking = generateKodeBooking(7);
                textViewKodeBooking.setText(kodeBooking);

                textViewTanggalPemesananKonfirmasi.setText(tanggalPemesanan);
                textViewJamPemesananKonfirmasi.setText(jamPemesanan);
                textViewZonaWaktuKonfirmasi.setText(zonaWaktu);
                textViewTujuanKonfirmasi.setText(tujuan);
                textViewLocationKonfirmasi.setText(location);
                textViewExtraCcKonfirmasi.setText(extra);
                textViewLayananPengirimanKonfirmasi.setText(layanan);
                textViewNamaCustomerKonfirmasi.setText(namaCustomer);
                textViewTeleponCustomerKonfirmasi.setText(teleponCustomer);
                textViewEmailCustomerKonfirmasi.setText(emailCustomer);
                textViewHargaTotalKonfirmasi.setText(hargaTotal);
                relativeLayoutPesanan.setVisibility(View.GONE);
                relativeLayoutKonfirmasi.setVisibility(View.VISIBLE);
            }
        });
    }

    private void costumer() {
        btnBackCostumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutCustomer.setVisibility(View.GONE);
                relativeLayoutCc.setVisibility(View.VISIBLE);
            }
        });

        btnNextCostumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNamaCustomer.getText().toString().isEmpty()) {
                    editTextNamaCustomer.setError("Nama tidak boleh kosong!");
                    editTextNamaCustomer.requestFocus();
                }else{
                    editTextNamaCustomer.setError(null);
                }

                if (editTextTeleponCustomer.getText().toString().isEmpty()) {
                    editTextTeleponCustomer.setError("Telepon tidak boleh kosong!");
                    editTextTeleponCustomer.requestFocus();
                }else{
                    editTextTeleponCustomer.setError(null);
                }

                if (editTextEmail.getText().toString().isEmpty()) {
                    editTextEmail.setError("Email tidak boleh kosong!");
                    editTextEmail.requestFocus();
                }else{
                    editTextEmail.setError(null);
                }

                if (!editTextNamaCustomer.getText().toString().isEmpty() && !editTextTeleponCustomer.getText().toString().isEmpty()
                        && !editTextEmail.getText().toString().isEmpty()){

//                    customerModel.setNama(editTextNamaCustomer.getText().toString());
//                    customerModel.setTelepon(editTextTeleponCustomer.getText().toString());
//                    customerModel.setEmail(editTextEmail.getText().toString());

                    textViewNamaCustomer.setText(editTextNamaCustomer.getText().toString());
                    textViewTeleponCustomer.setText(editTextTeleponCustomer.getText().toString());
                    textViewEmailCustomer.setText(editTextEmail.getText().toString());

                    relativeLayoutCustomer.setVisibility(View.GONE);
                    relativeLayoutPesanan.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void cc() {

        btnBackCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutCc.setVisibility(View.GONE);
                relativeLayoutTanggal.setVisibility(View.VISIBLE);
            }
        });

        btnNextCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_extra_cc.isChecked()){
//                    pemesananModel.setExtra("Diatas 2000cc");
//                    pemesananModel.setHarga("500000");
                    textViewExtraCc.setText("Diatas 2000cc");
                }else {
//                    pemesananModel.setExtra("Dibawah 2000cc");
//                    pemesananModel.setHarga("0");
                    textViewExtraCc.setText("Dibawah 2000cc");

                }

                System.out.println(pemesananModel.getExtra() +" "+pemesananModel.getHarga());
                relativeLayoutCc.setVisibility(View.GONE);
                relativeLayoutCustomer.setVisibility(View.VISIBLE);
            }
        });

    }


    private void tanggal() {
        cardViewTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hello tanggal");
                Calendar calendar = Calendar.getInstance();
                calendar.clear(); // menghapus jam dan menit untuk membuat tampilan kalender lebih bersih
                Long today = MaterialDatePicker.todayInUtcMilliseconds();
                calendar.setTimeInMillis(today);

                // Membuat instance MaterialDatePicker untuk memilih tanggal
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Pilih Tanggal");
                builder.setSelection(today);
                MaterialDatePicker<Long> materialDatePicker = builder.build();

                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    // Eksekusi ketika pengguna memilih tanggal
                    Calendar selectedDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    selectedDate.setTimeInMillis(selection);

                    // Format tanggal ke dalam bentuk string yang diinginkan
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = formatter.format(selectedDate.getTime());
                    Log.d("DATE", formattedDate);
                    textViewTanggalDipilih.setText("Tanggal dipilih: "+formattedDate);

                });

                materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");

            }
        });

        cardViewWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hello waktu");
                // Ambil waktu saat ini
                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                // Buat instance TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        (myView, hourOfDay, minute) -> {
                            // Format waktu yang dipilih
                            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

                            // Tampilkan waktu yang dipilih
                            textViewWaktuDipilih.setText("Waktu dipilih: "+selectedTime);
                        },
                        currentHour,
                        currentMinute,
                        false

                );

                // Tampilkan dialog untuk TimePickerDialog
                timePickerDialog.show();



            }
        });

        btnNextTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewTanggalDipilih.getText().toString().equals("Tanggal dipilih:")) {
                    btnNextTanggal.setError("Pilih Tanggal!");
//                    textViewTanggalDipilih.requestFocus();
                }else{
                    btnNextTanggal.setError(null);
                }

                if (textViewWaktuDipilih.getText().toString().equals("Waktu dipilih:")) {
                    btnNextTanggal.setError("Pilih Waktu!");
//                    textViewWaktuDipilih.requestFocus();
                }else {
                    btnNextTanggal.setError(null);
                }

                if (!textViewTanggalDipilih.getText().toString().equals("Tanggal dipilih:") && !textViewWaktuDipilih.getText().toString().equals("Waktu dipilih:")) {
                    String textViewTanggal[] = textViewTanggalDipilih.getText().toString().split(": ");
                    String tanggal = textViewTanggal[1];
                    String textViewWaktu[] = textViewWaktuDipilih.getText().toString().split(": ");
                    String waktu = textViewWaktu[1];
                    TimeZone timeZone = TimeZone.getDefault();

//                    pemesananModel.setTanggal(tanggal);
//                    pemesananModel.setJam(waktu);
//                    pemesananModel.setZonaWaktu(timeZone.getID());
                    textViewTanggalPemesanan.setText(tanggal);
                    textViewJamPemesanan.setText(waktu);
                    textViewZonaWaktu.setText(timeZone.getID());

//                    System.out.println(pemesananModel.getTanggal() +" - "+pemesananModel.getJam() +" - "+pemesananModel.getZonaWaktu());

                    relativeLayoutTanggal.setVisibility(View.GONE);
                    relativeLayoutCc.setVisibility(View.VISIBLE);
                }
            }
        });

        btnBackTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutTanggal.setVisibility(View.GONE);
                relativeLayoutTujuan.setVisibility(View.VISIBLE);
            }
        });

    }

    private void tujuan() {

        ArrayList<TujuanModel> tujuanModels = new ArrayList<>();
        tujuanModels.add(new TujuanModel("Bandung", "Rp2,200,000,-"));
        tujuanModels.add(new TujuanModel("Banten", "Rp2,200,000,-"));
        tujuanModels.add(new TujuanModel("Bengkulu", "Rp3,600,000,-"));
        tujuanModels.add(new TujuanModel("Jambi", "Rp4,100,000,-"));
        tujuanModels.add(new TujuanModel("Lampung", "Rp2,600,000,-"));
        tujuanModels.add(new TujuanModel("Manado", "Rp2,600,000,-"));
        tujuanModels.add(new TujuanModel("Medan", "Rp2,600,000,-"));
        tujuanModels.add(new TujuanModel("Padang", "Rp5,200,000,-"));
        tujuanModels.add(new TujuanModel("Serang", "Rp2,400,000,-"));
        tujuanModels.add(new TujuanModel("Semarang", "Rp2,400,000,-"));
        tujuanModels.add(new TujuanModel("Yogyakarta", "Rp3,000,000,-"));

        TujuanAdapter adapter = new TujuanAdapter(getActivity(), tujuanModels);

        listViewTujuan.setAdapter(adapter);

        listViewTujuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected Item: " + parent.getItemAtPosition(position) +" : "+ tujuanModels.get(position).getLokasi(), Toast.LENGTH_SHORT).show();
//                pemesananModel.setTujuan(String.valueOf(tujuanModels.get(position).getLokasi()));
//                pemesananModel.setHarga(String.valueOf(tujuanModels.get(position).getHarga()));
//                System.out.println(pemesananModel.getTujuan() +" : "+pemesananModel.getHarga());

                textViewTujuan.setText(String.valueOf(tujuanModels.get(position).getLokasi()));
                textViewHargaTotal.setText(String.valueOf(tujuanModels.get(position).getHarga()));

                relativeLayoutTujuan.setVisibility(View.GONE);
                relativeLayoutTanggal.setVisibility(View.VISIBLE);
            }
        });

        btnBackTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutTujuan.setVisibility(View.GONE);
                relativeLayoutLayanan.setVisibility(View.VISIBLE);
            }
        });

    }

    private void layanan() {

        cardViewSelfDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pemesananModel.setLayanan("Self Drive");
//                System.out.println(pemesananModel.getLayanan());
                textViewLayananPengiriman.setText("Self Drive");

                relativeLayoutLayanan.setVisibility(View.GONE);
                relativeLayoutTujuan.setVisibility(View.VISIBLE);
            }
        });

        cardViewCarCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pemesananModel.setLayanan("Car Carrier");
//                System.out.println(pemesananModel.getLayanan());
                textViewLayananPengiriman.setText("Car Carrier");

                relativeLayoutLayanan.setVisibility(View.GONE);
                relativeLayoutTujuan.setVisibility(View.VISIBLE);
            }
        });

        cardViewKapalRoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pemesananModel.setLayanan("Kapal Roro");
//                System.out.println(pemesananModel.getLayanan());
                textViewLayananPengiriman.setText("Kapal Roro");

                relativeLayoutLayanan.setVisibility(View.GONE);
                relativeLayoutTujuan.setVisibility(View.VISIBLE);
            }
        });

        cardViewTowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pemesananModel.setLayanan("Towing");
//                System.out.println(pemesananModel.getLayanan());
                textViewLayananPengiriman.setText("Towing");

                relativeLayoutLayanan.setVisibility(View.GONE);
                relativeLayoutTujuan.setVisibility(View.VISIBLE);
            }
        });

        cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pemesananModel.setLayanan("Container");
//                System.out.println(pemesananModel.getLayanan());
                textViewLayananPengiriman.setText("Container");
                relativeLayoutLayanan.setVisibility(View.GONE);
                relativeLayoutTujuan.setVisibility(View.VISIBLE);
            }
        });

        btnBackLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutLayanan.setVisibility(View.GONE);
                relativeLayoutLokasi.setVisibility(View.VISIBLE);
            }
        });
    }

    private void lokasi() {
        cardViewJakarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLocation.setText("DKI Jakarta");

                relativeLayoutLokasi.setVisibility(View.GONE);
                relativeLayoutLayanan.setVisibility(View.VISIBLE);
            }
        });

        cardViewSurabaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLocation.setText("Surabaya");
                relativeLayoutLokasi.setVisibility(View.GONE);
                relativeLayoutLayanan.setVisibility(View.VISIBLE);

            }
        });
    }

    private void createPdf() {
        View v = getActivity().getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        // Buat dokumen PDF baru
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        // Gambar bitmap ke halaman PDF
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(bitmap, 0, 0, null);

        // Selesaikan halaman dan dokumen PDF
        document.finishPage(page);
        String filename = "cetak.pdf";

        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        String filePath = directoryPath + File.separator + filename;
        File pdfFile = new File(filePath);
        // Cek apakah aplikasi memiliki izin untuk menulis ke penyimpanan eksternal

        try {

            FileOutputStream fos = new FileOutputStream(pdfFile);
            document.writeTo(fos);

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();
    }

    public static String generateKodeBooking(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder resi = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            resi.append(characters.charAt(index));
        }

        return resi.toString();
    }


}