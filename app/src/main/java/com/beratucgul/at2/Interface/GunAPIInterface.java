package com.beratucgul.at2.Interface;

import com.beratucgul.at2.ApiData.GunCekData;
import com.beratucgul.at2.GunEkle.GunSil;
import com.beratucgul.at2.Model.GunEklePostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GunAPIInterface {

    @FormUrlEncoded
    @POST("days")
    Call<GunEklePostModel> at (@Field("day") String day);

    @GET("days")
    Call<List<GunCekData>> getGunCekData();

    @DELETE("days/{id}")
    Call<GunSil> deleteGun(@Path("id") int id);

}
