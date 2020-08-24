package com.adida.dailycook.retrofit2.services;

import com.adida.dailycook.retrofit2.entities.Recipe;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeService {
    @GET("/recipe/all")
    Call<List<Recipe>> getAllRecipes(@Query("page") int page);
}
