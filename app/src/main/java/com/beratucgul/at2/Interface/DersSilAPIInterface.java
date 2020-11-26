package com.beratucgul.at2.Interface;

import com.beratucgul.at2.DersEkle.DersSil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DersSilAPIInterface {


    @DELETE("lessons/{id}")
    Call<DersSil> deleteData(@Path("id") int id);







}
