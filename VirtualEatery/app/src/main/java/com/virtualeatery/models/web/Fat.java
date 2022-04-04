package com.virtualeatery.models.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fat {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("unit")
    @Expose
    private String unit;

    public Integer getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}

