package com.beratucgul.at2.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GunCekData {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("day")
    @Expose
    public String day;

    public GunCekData(int id ,String day) {
        this.id = id;
        this.day = day;
    }
    public Integer getDayId() {
        return id;
    }
}
