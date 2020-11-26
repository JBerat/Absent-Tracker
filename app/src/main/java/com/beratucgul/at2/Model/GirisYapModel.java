package com.beratucgul.at2.Model;

import com.beratucgul.at2.ApiData.GirisYapData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GirisYapModel {


    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public GirisYapData girisYapDataList= null ;





}
