package com.virtualeatery.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.virtualeatery.models.db.DBHandler;
import com.virtualeatery.models.web.ImageResultsModel;
import com.virtualeatery.models.web.RetrofitClient;
import com.virtualeatery.models.web.SearchResultsModel;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessRequests {


    public void getNutritionalInfo(Context context, String TAG, String query, String apiKey) {
        SearchResultsModel[] nutrientsList = new SearchResultsModel[1];
        Call<SearchResultsModel> call = RetrofitClient.getInstance().getMyApi().getNutrientInfo(apiKey, query);

        call.enqueue(new Callback<SearchResultsModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchResultsModel> call, @NonNull Response<SearchResultsModel> response) {
                nutrientsList[0] = response.body();
                Intent intent = new Intent(context, NutritionalInformationActivity.class);
                intent.putExtra("calories", nutrientsList[0].getCalories().getValue().toString());
                intent.putExtra("carbohydrates", nutrientsList[0].getCarbs().getValue().toString());
                intent.putExtra("fats", nutrientsList[0].getFat().getValue().toString());
                intent.putExtra("proteins", nutrientsList[0].getProtein().getValue().toString());
                intent.putExtra("query", query);

                DBHandler dbHandler = new DBHandler(context);

                //checks if data is in the DB
                //prevents data duplication
                if(dbHandler.isDataInDB("nutrientsTable", "meal", query))
                    dbHandler.addNewNutrientInfo(query, nutrientsList[0].getCalories().getValue().toString(),
                            nutrientsList[0].getCarbs().getValue().toString(), nutrientsList[0].getFat().getValue().toString(),
                            nutrientsList[0].getProtein().getValue().toString());

                context.startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<SearchResultsModel> call, @NonNull Throwable t) {
                Log.v(TAG, "An error has occurred: " + t.getMessage());
            }
        });
    }

    public void getImageInfo(byte[] file, String apiKey, Context context) {

        final ImageResultsModel[] imageResultsModel = {new ImageResultsModel()};
        RequestBody requestBody = RequestBody
                .create(MediaType.parse("image/*"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", requestBody.toString(), requestBody);


        Call<ImageResultsModel> imageResultsModelCall = RetrofitClient.getInstance().getMyApi().getImageInfo (apiKey, body);

        imageResultsModelCall.enqueue(new Callback<ImageResultsModel>() {

            @Override
            public void onResponse(@NonNull Call<ImageResultsModel> call, @NonNull Response<ImageResultsModel> response) {
                imageResultsModel[0] = response.body();

                assert imageResultsModel[0] != null;
                Log.v("pleaselog", imageResultsModel[0].getNutrition().getCalories().getValue().toString());
                String meal = imageResultsModel[0].getCategory().getName();

                Intent intent = new Intent(context, NutritionalInformationActivity.class);
                intent.putExtra("calories", imageResultsModel[0].getNutrition().getCalories().getValue().toString());
                intent.putExtra("carbohydrates", imageResultsModel[0].getNutrition().getCarbs().getValue().toString());
                intent.putExtra("fats", imageResultsModel[0].getNutrition().getFat().getValue().toString());
                intent.putExtra("proteins", imageResultsModel[0].getNutrition().getProtein().getValue().toString());
                intent.putExtra("query", meal);

                DBHandler dbHandler = new DBHandler(context);

                //checks if data is in the DB
                //prevents data duplication
                if(dbHandler.isDataInDB("nutrientsTable", "meal", meal))
                    dbHandler.addNewNutrientInfo(meal, imageResultsModel[0].getNutrition().getCalories().getValue().toString(),
                            imageResultsModel[0].getNutrition().getCarbs().getValue().toString(), imageResultsModel[0].getNutrition().getFat().getValue().toString(),
                            imageResultsModel[0].getNutrition().getProtein().getValue().toString());

                context.startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<ImageResultsModel> call, @NonNull Throwable t) {
                Log.v("pleaselog", t.getMessage());
            }
        });
    }
}
