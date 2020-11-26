package com.beratucgul.at2.Interface;

import com.beratucgul.at2.ApiData.ProgramCekData;
import com.beratucgul.at2.Model.ProgramCekPostModel;
import com.beratucgul.at2.Model.ProgramEklePostModel;
import com.beratucgul.at2.ProgramEkle.ProgramSil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProgramAPIInterface {


    @FormUrlEncoded
    @POST("programs")
    Call<ProgramEklePostModel> at (@Field("userid") int userId,
                                   @Field("lessonid") String lessonId,
                                   @Field("dayid") Integer dayId,
                                   @Field("absent") String absent,
                                   @Field("hour") String hour);


    @GET("programs/{id}")
    Call<ProgramCekPostModel> getProgramCekData(@Path("id") int userId);
                                                //@Header("phoneToken") String phoneToken);
                                                //@Header("Authorization") String token);

    @DELETE("programs/{id}")
    Call<ProgramSil> deleteProgram(@Path("id") int id);


   /* @GET("programs/{id}")
    Call<ProgramCekPostModel> getProgramGuncelleData(@Path("id") int id,
                                                     @Field("userid") int userId,
                                                     @Field("lessonid") String lessonId,
                                                     @Field("dayid") String dayId,
                                                     @Field("absent") String absent,
                                                     @Field("hour") String hour);

    */




}
