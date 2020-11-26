package com.beratucgul.at2.Model;

import com.beratucgul.at2.ApiData.ProgramEklePostData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProgramEklePostModel {

    @SerializedName("data")
    @Expose
    public List<ProgramEklePostData> programEklePostData;



}
