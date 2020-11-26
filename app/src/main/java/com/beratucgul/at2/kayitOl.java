package com.beratucgul.at2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beratucgul.at2.Interface.KayitOlAPIInterface;
import com.beratucgul.at2.Model.KayitOlModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class kayitOl extends AppCompatActivity {
    EditText kayitOlName, kayitOlEmail, kayitOlPassword;
    private static final String TAG = "MainActivity";
    String token;
    String userToken;


     public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";
    //public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";
    //public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        kayitOlName = findViewById(R.id.kayitOlName);
        kayitOlEmail = findViewById(R.id.kayitOlEmail);
        kayitOlPassword = findViewById(R.id.kayitOlPassword);

    }

    public void giriseDon(View view) {

        kayitOlPost();

            Intent intent = new Intent(kayitOl.this, MainActivity.class);
            startActivity(intent);
            finish();
    }

    public void kayitOlPost() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");


      /*  OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + userToken)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

       */



        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        KayitOlAPIInterface kayitOlAPIInterface = retrofit.create(KayitOlAPIInterface.class);
        Call<KayitOlModel> call = kayitOlAPIInterface.at(kayitOlName.getText().toString(), kayitOlEmail.getText().toString(),kayitOlPassword.getText().toString(),token);

        call.enqueue(new Callback<KayitOlModel>() {
            @Override
            public void onResponse(Call<KayitOlModel> call, Response<KayitOlModel> response) {

                Log.d("User Kayıt: " , response.message());

                Toast.makeText(kayitOl.this,response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<KayitOlModel> call, Throwable t) {
                Toast.makeText(kayitOl.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("User Kayıt Fail: ", t.getMessage());
            }
        });
    }

    public void giriseDonn(View view) {
        Intent intent = new Intent(kayitOl.this, MainActivity.class);
        startActivity(intent);
    }
}