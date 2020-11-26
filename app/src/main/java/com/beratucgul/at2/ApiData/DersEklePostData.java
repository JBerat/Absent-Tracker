package com.beratucgul.at2.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DersEklePostData {

    @SerializedName("lesson")
    @Expose
    public String lessons;

}
