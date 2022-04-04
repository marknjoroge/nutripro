package com.virtualeatery.models.db;

public class MealsTakenModel {

    private String theDate;
    private String meal;
    private String id;

    public MealsTakenModel( String id, String theDate, String meal) {
        this.id = id;
        this.theDate = theDate;
        this.meal = meal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheDate() {
        return theDate;
    }

    public String getMeal() {
        return meal;
    }

}
