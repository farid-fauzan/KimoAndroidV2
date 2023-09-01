package com.example.myapplication.util;

public class ParameterLoader {
    public String URL(){
        return "http://103.157.96.244:8191";
    }

    public interface STATUS_PESANAN{
        public String PROSES="1";
        public String PAID="2";
        public String PERJALANAN="3";
        public String SELESAI="5";
        public String GAGAL="0";
    }
}
