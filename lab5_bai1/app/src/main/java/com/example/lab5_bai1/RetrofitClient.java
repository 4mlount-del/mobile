package com.example.lab5_bai1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // LUU Y QUAN TRONG VE URL:
    // - Chay tren Emulator: dung http://10.0.2.2/lab5/
    // - Chay tren dien thoai that (chung WiFi voi PC): dung http://<IP-PC>/lab5/
    //   VD: http://192.168.1.10/lab5/
    // - Khong dung http://localhost/ truc tiep tren Android vi localhost
    //   tren dien thoai/emulator khong phai la PC chay XAMPP.
    private static final String BASE_URL = "http://10.45.110.59/lab5/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static MyAPI getApi() {
        return getClient().create(MyAPI.class);
    }
}
