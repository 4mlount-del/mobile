package com.example.lab5_bai1;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
