package com.beratucgul.at2.DersEkle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beratucgul.at2.Interface.DersEklePostAPIInterface;
import com.beratucgul.at2.Model.DersEklePostModel;
import com.beratucgul.at2.R;

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


public class DersEkle extends AppCompatActivity {


    public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";
   // public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";
    ImageView BackToEklenilenDers;
    TextView Dersler;
    EditText dersEditText;
    String dersSec;
    String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ders_ekle);

        dersEditText = findViewById(R.id.dersEditText);
        BackToEklenilenDers = findViewById(R.id.BackToEklenilenDersler);
        Dersler = findViewById(R.id.Dersler);
    }

    public void goBackToEklenilenDersler(View view) {



        dersEklePost();

        Intent intent = new Intent(DersEkle.this, EklenilenDersler.class);

        dersSec = dersEditText.getText().toString();

        intent.putExtra("ders",dersSec);

        startActivity(intent);
    }

    public void dersEklePost() {

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



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        DersEklePostAPIInterface dersEklePostAPIInterface = retrofit.create(DersEklePostAPIInterface.class);

        Call<DersEklePostModel> dersEklePostModelCall = dersEklePostAPIInterface.at(dersEditText.getText().toString());
        dersEklePostModelCall.enqueue(new Callback<DersEklePostModel>() {

            @Override
            public void onResponse(Call<DersEklePostModel> call, Response<DersEklePostModel> response) {
                Toast.makeText(DersEkle.this,response.message(),Toast.LENGTH_LONG).show();
                dersSec = dersEditText.getText().toString();
            }

            @Override
            public void onFailure(Call<DersEklePostModel> call, Throwable t) {
                Toast.makeText(DersEkle.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}