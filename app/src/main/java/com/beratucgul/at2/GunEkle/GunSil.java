package com.beratucgul.at2.GunEkle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GunSil {

    @SerializedName("id")
    @Expose
    public int id;

    public GunSil(int id) {
        this.id = id;
    }
}
