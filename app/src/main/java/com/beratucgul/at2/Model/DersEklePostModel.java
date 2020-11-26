package com.beratucgul.at2.Model;

import com.beratucgul.at2.ApiData.DersEklePostData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DersEklePostModel {

    @SerializedName("data")
    @Expose
    public List<DersEklePostData> dersEklePostData;

}
