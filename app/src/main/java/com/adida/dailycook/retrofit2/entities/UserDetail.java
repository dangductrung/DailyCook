package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDetail implements Serializable {
    @SerializedName("data")
    private ArrayList<User> user;

    public ArrayList<User> getList() {
        return user;
    }
}
