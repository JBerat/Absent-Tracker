package com.beratucgul.at2.Model;

import com.beratucgul.at2.ApiData.GunEklePostData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GunEklePostModel {

    @SerializedName("data")
    @Expose
    public List<GunEklePostData> gunEklePostData;

}
