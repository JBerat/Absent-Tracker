package com.beratucgul.at2.ProgramEkle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgramSil {

    @SerializedName("id")
    @Expose
    public int id;

    public ProgramSil(int id) {
        this.id = id;
    }
}
