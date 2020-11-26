package com.beratucgul.at2.Interface;

import com.beratucgul.at2.DersEkle.DersEkle;
import com.beratucgul.at2.Model.DersEklePostModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DersEklePostAPIInterface {

    @FormUrlEncoded
    @POST("lessons")
    Call<DersEklePostModel> at (@Field("lesson") String lesson);

}
