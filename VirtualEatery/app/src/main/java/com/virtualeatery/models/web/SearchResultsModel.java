package com.virtualeatery.models.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultsModel {

    @SerializedName("recipesUsed")
    @Expose
    private Integer recipesUsed;
    @SerializedName("calories")
    @Expose
    private Calories calories;
    @SerializedName("fat")
    @Expose
    private Fat fat;
    @SerializedName("protein")
    @Expose
    private Protein protein;
    @SerializedName("carbs")
    @Expose
    private Carbs carbs;
    public Calories getCalories() {
        return calories;
    }

    public void setCalories(Calories calories) {
        this.calories = calories;
    }

    public Fat getFat() {
        return fat;
    }
    public Protein getProtein() {
        return protein;
    }

    public Carbs getCarbs() {
        return carbs;
    }
}

