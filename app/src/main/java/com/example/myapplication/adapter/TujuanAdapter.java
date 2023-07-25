package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.TujuanModel;

import java.util.ArrayList;

public class TujuanAdapter extends ArrayAdapter<TujuanModel> {

    public TujuanAdapter(Context context, ArrayList<TujuanModel> tujuanModels) {
        super(context, 0, tujuanModels);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TujuanModel tujuanModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tujuan, parent, false);
        }

        TextView textViewLokasi = convertView.findViewById(R.id.text_lokasi_tujuan);
        TextView textViewHarga = convertView.findViewById(R.id.text_harga);

        textViewLokasi.setText(tujuanModel.getLokasi());
        textViewHarga.setText(tujuanModel.getHarga());

        return convertView;
    }
}

