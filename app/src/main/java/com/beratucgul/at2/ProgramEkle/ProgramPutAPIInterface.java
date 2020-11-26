package com.beratucgul.at2.ProgramEkle;

import com.beratucgul.at2.ApiData.ProgramCekData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProgramPutAPIInterface {

   /* @FormUrlEncoded
    @POST("programs/{id}")
    Call<ProgramPut> updateProgram(@Path("id") int id,
                                       @Field("lessonid") String lessonId,
                                       @Field("dayid") String dayId,
                                       @Field("absent") String absent,
                                       @Field("hour") String hour);

    */





/*  @POST("programs/{id}")
  Call<ProgramPut> updateProgram(@Body ProgramPut programPut);

 */
    @FormUrlEncoded
    @POST("programs/{id}")
    Call<ProgramPut> updateProgramId(@Field("id") int id);

    @FormUrlEncoded
    @PUT("programs/{id}")
    Call<ProgramPut> at(@Path("id") int id,
                        @Field("userid") int userid,
                        @Field("lessonid") String lessonId,
                        @Field("dayid") String dayId,
                        @Field("absent") String absent,
                        @Field("hour") String hour);





}
