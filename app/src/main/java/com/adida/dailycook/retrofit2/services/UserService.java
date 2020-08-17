package com.adida.dailycook.retrofit2.services;

import com.adida.dailycook.retrofit2.entities.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("/")
    Call<User> getUserInformation();
}
