package com.adida.dailycook.retrofit2.services;

import com.adida.dailycook.retrofit2.entities.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TagService {
    @GET("/tags")
    Call<List<Tag>> getAllTags();
}
