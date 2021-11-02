package com.rbc.yelp.services.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Region implements Serializable {

    @SerializedName("center")
    @Expose
    private Coordinates center;

    public Region() {
    }

    public Coordinates getCenter() {
        return center;
    }

    public void setCenter(Coordinates center) {
        this.center = center;
    }
}
