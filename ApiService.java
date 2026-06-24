package com.example.assmohamedfaridyoutube;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search")
    Call<JsonObject> searchVideos(
            @Query("part") String part,
            @Query("type") String type,
            @Query("q") String query,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
}