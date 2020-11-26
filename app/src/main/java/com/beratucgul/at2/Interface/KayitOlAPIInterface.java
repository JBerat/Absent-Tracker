package com.beratucgul.at2.Interface;

import com.beratucgul.at2.Model.KayitOlModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KayitOlAPIInterface {

    @FormUrlEncoded
    @POST("users")
    Call<KayitOlModel> at(@Field("name") String name,
                          @Field("email") String email,
                          @Field("password") String password,
                          @Field("phoneToken") String phoneToken);
}
