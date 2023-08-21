package com.example.myapplication.util;

public class ParameterLoader {
    public String URL(){
        return "http://192.168.78.153:8190";
    }

    public interface STATUS_PESANAN{
        public String PROSES="1";
        public String DRIVER="2";
        public String PENGANTARAN="3";
        public String SELESAI="5";
        public String GAGAL="0";
    }
}
