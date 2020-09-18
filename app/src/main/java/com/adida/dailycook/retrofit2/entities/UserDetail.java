package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDetail {
    @SerializedName("data")
    private ArrayList<User> user;

    public ArrayList<User> getList() {
        return user;
    }
}
