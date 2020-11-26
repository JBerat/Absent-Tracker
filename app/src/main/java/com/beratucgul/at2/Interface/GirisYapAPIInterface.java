package com.beratucgul.at2.Interface;

import com.beratucgul.at2.ApiData.GirisYapData;
import com.beratucgul.at2.Model.GirisYapModel;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface GirisYapAPIInterface {


    //@Headers({ "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImJlbjJAZ21haWwuY29tIiwiaWF0IjoxNjAxNTg2MDMzLCJleHAiOjE2MDE1OTMyMzN9.dNbs8FKVDe-fwn34oKgCY90-IdVE7S0TbV_ACzWlbro" })
    @FormUrlEncoded
    @POST("users/login")
    Call<GirisYapModel> at(//@Header("token") String token,
                           @Field("email") String girisEmail,
                           @Field("password") String girisPassword);

    //@Header("token") String girisToken,
    //@Field("token") String token




}


