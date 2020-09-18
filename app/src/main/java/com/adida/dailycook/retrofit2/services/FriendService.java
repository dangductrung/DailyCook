package com.adida.dailycook.retrofit2.services;


import com.adida.dailycook.retrofit2.entities.User;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FriendService {
    @GET("/friend/all")
    Call<ArrayList<User>> getAllFriend(@Query("id") int id);

    @POST("friend/add")
    Call<Map<String, String>> addFriend(@Body() Map<String, Object> params);

    @HTTP(method = "DELETE", path = "/friend/remove", hasBody = true)
    Call<Map<String, String>> deleteFriend(@Body() Map<String, Object> params);

    @GET("/friend/isFriend")
    Call<Boolean> isFriend(@Query("user_id") String uid, @Query("friend_id") int fid);
}
