package com.beratucgul.at2.Interface;

import com.beratucgul.at2.ApiData.DersCekData;
import com.beratucgul.at2.DersEkle.DersPut;
import com.beratucgul.at2.DersEkle.DersSil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DersCekAPIInferface {

    @GET("lessons")
    Call<List<DersCekData>> getDersCekData();



    @PUT("/lessons/{id}")
    Call<DersCekData> putDersData(@Path("id") int id , @Body DersCekData dersCekData);





 /*   @HTTP(method = "DELETE", path = "/lessons/:{id}/", hasBody = true)
    Call<DersCekData> deleteObject(@Body DersCekData object);

  */
}





