package com.beratucgul.at2.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgramCekData {

    @SerializedName("id")
    @Expose
    public int id;


  /*  @SerializedName("userid")
    @Expose
    public  int userId;

   */
    @SerializedName("lessonid")
    @Expose
    public String lessonId;
    @SerializedName("dayid")
    @Expose
    public String dayId;
    @SerializedName("absent")
    @Expose
    public String absent;
    @SerializedName("hour")
    @Expose
    public String hour;

    @SerializedName("token")
    @Expose
    public String token;


    /*
    @SerializedName("phoneToken")
    @Expose
    public  String phoneToken;

    */


    public ProgramCekData( String lessonId, String dayId, String absent, String hour, String token, String phoneToken) {
        this.lessonId = lessonId;
        this.dayId = dayId;
        this.absent = absent;
        this.hour = hour;
        this.token = token;

    }



    public Integer getProgramId() {
        return  id;
    }

}
