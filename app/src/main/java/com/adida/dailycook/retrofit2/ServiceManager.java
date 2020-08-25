package com.adida.dailycook.retrofit2;

import com.adida.dailycook.config.Config;
import com.adida.dailycook.retrofit2.services.RecipeService;
import com.adida.dailycook.retrofit2.services.UserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceManager {
    private ServiceManager() {
        initRetrofit();
    }

    private static ServiceManager instance = new ServiceManager();
    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    private Retrofit retrofit;

    private void initRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl(Config.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public UserService getUserService() {
        return retrofit.create(UserService.class);
    }

    public RecipeService getRecipeService() {
        return retrofit.create(RecipeService.class);
    }
}
