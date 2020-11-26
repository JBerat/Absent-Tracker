package com.beratucgul.at2.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgramEklePostData {


    @SerializedName("lessonid")
    @Expose
    public Integer lessonId;
    @SerializedName("dayid")
    @Expose
    public Integer dayId;
    @SerializedName("absent")
    @Expose
    public String absent;
    @SerializedName("hour")
    @Expose
    public String hour;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("phoneToken")
    @Expose
    public  String phoneToken;
}
