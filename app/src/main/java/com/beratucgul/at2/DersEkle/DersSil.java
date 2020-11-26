package com.beratucgul.at2.DersEkle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DersSil {

    @SerializedName("id")
    @Expose
    public int id;

    public DersSil(int id) {
        this.id = id;
    }
}
