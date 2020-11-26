package com.beratucgul.at2.Model;

import com.beratucgul.at2.ApiData.GirisYapData;
import com.beratucgul.at2.ApiData.ProgramCekData;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;

public class ProgramCekPostModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    private List<ProgramCekData> programCekDataList= null;

    public List<ProgramCekData> getProgramCekDataList() {
        return programCekDataList;
    }



}
