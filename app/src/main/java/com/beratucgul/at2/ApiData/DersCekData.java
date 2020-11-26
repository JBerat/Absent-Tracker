package com.beratucgul.at2.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DersCekData {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("lesson")
    @Expose
    public String lesson;

    public DersCekData(int id, String lesson) {
        this.id = id;
        this.lesson = lesson;
    }

    public Integer getLessonId() {
        return  id;
    }


}
