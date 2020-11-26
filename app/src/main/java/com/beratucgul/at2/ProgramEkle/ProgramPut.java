package com.beratucgul.at2.ProgramEkle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgramPut {

    @SerializedName("id")
    @Expose
    public int userid;
    @SerializedName("userid")
    @Expose
    public int id;
    @SerializedName("lessonid")
    @Expose
    public String lessonId;
    @SerializedName("dayid")
    @Expose
    public Integer dayId;
    @SerializedName("absent")
    @Expose
    public String absent;
    @SerializedName("hour")
    @Expose
    public String hour;

    public ProgramPut(int id) {
        this.id = id;
    }
}
