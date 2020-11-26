package com.beratucgul.at2.ProgramEkle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.beratucgul.at2.ApiData.ProgramCekData;
import com.beratucgul.at2.ApiData.ProgramEklePostData;
import com.beratucgul.at2.GunEkle.EklenilenGunler;
import com.beratucgul.at2.Interface.ProgramAPIInterface;
import com.beratucgul.at2.Model.ProgramCekPostModel;
import com.beratucgul.at2.Model.ProgramEklePostModel;
import com.beratucgul.at2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProgramGuncelle extends AppCompatActivity {

  //  public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";

    public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";

    //public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";
    EditText dersGuncelleEdit, gunGuncelleEdit, devamsızlıkGuncelleEdit, saatGuncelleEdit;
    ImageView programGuncelleButton;
    Retrofit retrofit;
    SharedPreferences sharedPreferences1;
    String userToken;
    ImageView goBackToUpdateDays;
    Spinner dayUpdateSpinner;
    public int dayUpdateSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_guncelle);

        dersGuncelleEdit = findViewById(R.id.dersGuncelleEdit);
        gunGuncelleEdit = findViewById(R.id.gunGuncelleEdit);
        devamsızlıkGuncelleEdit = findViewById(R.id.devamsızlıkGuncelleEdit);
        saatGuncelleEdit = findViewById(R.id.saatGuncelleEdit);
        programGuncelleButton = findViewById(R.id.programGuncelleButton);
        goBackToUpdateDays = findViewById(R.id.goBackUpdateToDays);
       // dayUpdateSpinner = findViewById(R.id.dayUpdateSpinner);



    /*    SharedPreferences sharedPreferences3 = getSharedPreferences("dayId", MODE_PRIVATE);
        dayUpdateSpin = sharedPreferences3.getInt("dayid", 0);
        //   daySpinnerArray = new Integer[daySpin];
        final ArrayList<Integer> dayId = new ArrayList<>();
        dayId.add(dayUpdateSpin);
        ArrayAdapter dayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dayId);
        dayUpdateSpinner.setAdapter(dayAdapter);

     */

  /*  SharedPreferences sharedPreferences = getSharedPreferences("dayId", MODE_PRIVATE);
    gunGuncelleEdit.setText(sharedPreferences.getInt("dayid", 0));

   */








        SharedPreferences sharedPreferences2 = getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = sharedPreferences2.getString("userToken", "");

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + userToken)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();


        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();


        sharedPreferences1 = getSharedPreferences("Update", MODE_PRIVATE);
        String lesson = sharedPreferences1.getString("lesson","");
        String day = sharedPreferences1.getString("day", "");
        String absent = sharedPreferences1.getString("absent", "");
        String hour = sharedPreferences1.getString("hour", "");

        dersGuncelleEdit.setText(lesson);
        gunGuncelleEdit.setText(day);
        devamsızlıkGuncelleEdit.setText(absent);
        saatGuncelleEdit.setText(hour);

    }



    public void goBackToEklenenProgramlar(View view) {


        SharedPreferences sharedPreferences2 = getSharedPreferences("Update1", MODE_PRIVATE);
        int programid = sharedPreferences2.getInt("ProgramId", 0);

      SharedPreferences  sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);

      /*  programCekDataArrayList = programRecyclerAdapter.programCekDataArrayList;
        int position = viewHolder.getAdapterPosition();

       */



        ProgramPutAPIInterface programPutAPIInterface = retrofit.create(ProgramPutAPIInterface.class);

        Call<ProgramPut> call = programPutAPIInterface.at(programid,id, dersGuncelleEdit.getText().toString(), gunGuncelleEdit.getText().toString(),
                                                                     devamsızlıkGuncelleEdit.getText().toString(),saatGuncelleEdit.getText().toString());
        call.enqueue(new Callback<ProgramPut>() {
            @Override
            public void onResponse(Call<ProgramPut> call, Response<ProgramPut> response) {


                SharedPreferences sharedPreferences3 = getApplicationContext().getSharedPreferences("Update2", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences3.edit();

                String ders = dersGuncelleEdit.getText().toString();
                String gun = gunGuncelleEdit.getText().toString();
            //    int gun = dayUpdateSpin;
                String devamsızlık = devamsızlıkGuncelleEdit.getText().toString();
                String saat = saatGuncelleEdit.getText().toString();

                editor2.putString("ders", ders);
                editor2.putString("gun", gun);
                editor2.putString("devamsızlık", devamsızlık);
                editor2.putString("saat", saat);

                editor2.commit();



                System.out.println("Program id " + programid);
                System.out.println("Program User id " + id);

                Log.d("Güncellenen Program", response.message());
                Toast.makeText(ProgramGuncelle.this, response.message(),Toast.LENGTH_SHORT).show();
                System.out.println(response);

                Intent intent = new Intent(ProgramGuncelle.this, EklenenProgramlar.class);

                startActivity(intent);

            }

            @Override
            public void onFailure(Call<ProgramPut> call, Throwable t) {

                Log.d("Update Program Failed: ", t.getMessage());

            }
        });
    }

    public void goBackToUpdateDays(View view) {
        Intent intent = new Intent(ProgramGuncelle.this, EklenilenGunler.class);
        intent.putExtra("data", "old");
        startActivity(intent);
    }

}