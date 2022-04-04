package com.virtualeatery.models.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrition {

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

    public Nutrition() {
    }

    public Nutrition(Calories calories, Fat fat, Protein protein, Carbs carbs) {
        super();
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
    }

    public Calories getCalories() {
        return calories;
    }

    public void setCalories(Calories calories) {
        this.calories = calories;
    }

    public Fat getFat() {
        return fat;
    }

    public void setFat(Fat fat) {
        this.fat = fat;
    }

    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public Carbs getCarbs() {
        return carbs;
    }

    public void setCarbs(Carbs carbs) {
        this.carbs = carbs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Nutrition.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("recipesUsed");
        sb.append('=');
        sb.append(',');
        sb.append("calories");
        sb.append('=');
        sb.append(((this.calories == null)?"<null>":this.calories));
        sb.append(',');
        sb.append("fat");
        sb.append('=');
        sb.append(((this.fat == null)?"<null>":this.fat));
        sb.append(',');
        sb.append("protein");
        sb.append('=');
        sb.append(((this.protein == null)?"<null>":this.protein));
        sb.append(',');
        sb.append("carbs");
        sb.append('=');
        sb.append(((this.carbs == null)?"<null>":this.carbs));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}