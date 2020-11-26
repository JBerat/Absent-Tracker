package com.beratucgul.at2.ProgramEkle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.beratucgul.at2.GunEkle.EklenilenGunler;
import com.beratucgul.at2.Interface.ProgramAPIInterface;
import com.beratucgul.at2.Model.ProgramEklePostModel;
import com.beratucgul.at2.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProgramEkle extends AppCompatActivity {

    //public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";

    //public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";


    public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";
    EditText gunSecEdit;
    EditText dersSecEdit;
    EditText saatSecEdit;
    EditText devamsızlıkSecEdit;
    ImageView programEkleButton;
    String gunSec;
    String dersSec;
    String saatSec;
    String devamsızlıkSec;
    SharedPreferences sharedPreferences;
    int id;
    String userToken;
    ImageView goBack;
    private Integer daySpinnerArray[];
    public int daySpin;
    private Integer lessonSpinnerArray[];
    public int lessonSpin;
    Spinner daySpinner;
    Spinner lessonSpinner;
    ImageView goBackToDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_ekle);

       // gunSecEdit = findViewById(R.id.gunSecEdit);
        dersSecEdit = findViewById(R.id.dersSecEdit);
        saatSecEdit = findViewById(R.id.saatSecEdit);
        devamsızlıkSecEdit = findViewById(R.id.devamsızlıkSecEdit);
        programEkleButton = findViewById(R.id.programEkleButton);
        daySpinner = findViewById(R.id.daySpinner);
       // lessonSpinner = findViewById(R.id.lessonSpinner);
        goBack = findViewById(R.id.goBack);
        goBackToDays = findViewById(R.id.goBackToDays);





        SharedPreferences sharedPreferences3 = getSharedPreferences("dayId", MODE_PRIVATE);
        daySpin = sharedPreferences3.getInt("dayid", 0);
        //   daySpinnerArray = new Integer[daySpin];
        final ArrayList<Integer> dayId = new ArrayList<>();
        dayId.add(daySpin);
        ArrayAdapter dayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dayId);
        daySpinner.setAdapter(dayAdapter);





        SharedPreferences sharedPreferences2 = getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = sharedPreferences2.getString("userToken", "");









    /*    sharedPreferences1 = getSharedPreferences("Update", MODE_PRIVATE);

        String lessons = sharedPreferences1.getString("lesson", "");
        String days = sharedPreferences1.getString("day", "");
        String absents = sharedPreferences1.getString("absent", "");
        String hours = sharedPreferences1.getString("hour", "");

        gunSecEdit.setText(days);
        dersSecEdit.setText(lessons);
        devamsızlıkSecEdit.setText(absents);
        saatSecEdit.setText(hours);

     */



    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void backToEklenenProgramlar(View view) {

      programEklePost();

        Intent intentToEklenenProgramlar = new Intent(ProgramEkle.this, EklenenProgramlar.class);

      //  gunSec = gunSecEdit.getText().toString();
       // dersSec = dersSecEdit.getText().toString();
        saatSec = saatSecEdit.getText().toString();
        devamsızlıkSec = devamsızlıkSecEdit.getText().toString();

        intentToEklenenProgramlar.putExtra("program1", gunSec);
        intentToEklenenProgramlar.putExtra("program2", dersSec);
        intentToEklenenProgramlar.putExtra("program3", saatSec);
        intentToEklenenProgramlar.putExtra("program4", devamsızlıkSec);
        intentToEklenenProgramlar.putExtra("id", id);


        startActivity(intentToEklenenProgramlar);
        finish();
    }

    public void programEklePost() {

       /* OkHttpClient client = new OkHttpClient();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS).build();
        client = builder.build();

        */




      /*  OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        //     .addHeader("Content-Type", "application/json")
                        .addHeader("token",  "Bearer " + userToken)
                        .build();
                return chain.proceed(request);
            }
        });

       */
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




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();











      /*  SharedPreferences sharedPreferences4 = getSharedPreferences("lessonId", MODE_PRIVATE);
        lessonSpin = sharedPreferences4.getInt("lessonid", 0);
        lessonSpinnerArray = new Integer[lessonSpin];
        ArrayAdapter lessonAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, lessonSpinnerArray);
        lessonSpinner.setAdapter(lessonAdapter);

       */










        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
         id = sharedPreferences.getInt("id", 0);

        ProgramAPIInterface programAPIInterface = retrofit.create(ProgramAPIInterface.class);

        Call<ProgramEklePostModel> programEklePostModelCall = programAPIInterface.at(id ,dersSecEdit.getText().toString(),daySpin,devamsızlıkSecEdit.getText().toString(),
                saatSecEdit.getText().toString());

        programEklePostModelCall.enqueue(new Callback<ProgramEklePostModel>() {
            @Override
            public void onResponse(Call<ProgramEklePostModel> call, Response<ProgramEklePostModel> response) {
                Toast.makeText(ProgramEkle.this, response.message(), Toast.LENGTH_SHORT).show();

                Log.d("user", response.message());
            }

            @Override
            public void onFailure(Call<ProgramEklePostModel> call, Throwable t) {
                Toast.makeText(ProgramEkle.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.d("user1", t.getMessage());
                System.out.println(t);
            }
        });
    }

    public void goBack(View view) {
        Intent intent = new Intent(ProgramEkle.this, EklenenProgramlar.class);
        startActivity(intent);
    }
    public void goBackToDays(View view) {
        Intent intent = new Intent(ProgramEkle.this, EklenilenGunler.class);
        intent.putExtra("data", "new");
        startActivity(intent);
    }
}
