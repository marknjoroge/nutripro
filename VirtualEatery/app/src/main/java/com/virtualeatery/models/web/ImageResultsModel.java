package com.virtualeatery.models.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResultsModel {

    @SerializedName("nutrition")
    @Expose
    private Nutrition nutrition;
    @SerializedName("category")
    @Expose
    private Category category;

    public ImageResultsModel() {
    }

    public Nutrition getNutrition() {
        return nutrition;
    }


    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ImageResultsModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("nutrition");
        sb.append('=');
        sb.append(((this.nutrition == null)?"<null>":this.nutrition));
        sb.append(',');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
