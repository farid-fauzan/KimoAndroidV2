package com.example.myapplication.util;

public class ParameterLoader {
    public String URL(){
        return "http://10.237.5.30:8190";
    }

    public interface STATUS_PESANAN{
        public String PROSES="1";
        public String PAID="2";
        public String PERJALANAN="3";
        public String SELESAI="5";
        public String GAGAL="0";
    }
}
