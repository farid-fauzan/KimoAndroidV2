package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.navigation.ActionOnlyNavDirections;

import com.example.myapplication.R;
import com.example.myapplication.model.OrderModel;
import com.example.myapplication.model.TujuanModel;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<OrderModel> {

    public OrderAdapter(Context context, ArrayList<OrderModel> orderModels) {
        super(context, 0, orderModels);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderModel orderModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_pesanan, parent, false);
        }

        TextView textViewTujuan = convertView.findViewById(R.id.text_lokasi_tujuan);
        TextView textViewTanggal = convertView.findViewById(R.id.text_tanggal);
        TextView textViewJam = convertView.findViewById(R.id.text_jam);
        TextView textViewAgent = convertView.findViewById(R.id.text_agent);
        TextView textViewStatus = convertView.findViewById(R.id.text_status);

        textViewTujuan.setText(orderModel.getTujuan());
        textViewTanggal.setText(orderModel.getTanggal());
        textViewJam.setText(orderModel.getJam());
        textViewAgent.setText(orderModel.getAgent());
        textViewStatus.setText(orderModel.getStatus());

        return convertView;
    }
}

