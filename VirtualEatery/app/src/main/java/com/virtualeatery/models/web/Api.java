package com.virtualeatery.models.web;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://api.spoonacular.com/";

    //word query
    @GET("recipes/guessNutrition")
    Call <SearchResultsModel> getNutrientInfo(@Query("apiKey") String apiKey , @Query("title") String title);

    //image query
    @Multipart
    @POST("food/images/analyze")
    Call<ImageResultsModel> getImageInfo(@Query("apiKey") String apiKey,
                                         @Part MultipartBody.Part photo);
}