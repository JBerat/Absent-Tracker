package com.beratucgul.at2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beratucgul.at2.Interface.GirisYapAPIInterface;
import com.beratucgul.at2.Model.GirisYapModel;
import com.beratucgul.at2.ProgramEkle.EklenenProgramlar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";

   // public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";


    // public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";
    EditText girisYapEmail, girisYapPassword;
    public int id;
    SharedPreferences sharedPreferences;
    public String token;
    private static final String TAG = "MainActivity";
    public String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        girisYapEmail = findViewById(R.id.girisYapEmail);
        girisYapPassword = findViewById(R.id.girisYapPassword);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new Fcm registration token
                        token = task.getResult();

                        System.out.println("PHONE TOKEN  " + token);

                        SharedPreferences getToken = getApplicationContext().getSharedPreferences("Token", MODE_PRIVATE);
                        SharedPreferences.Editor editor = getToken.edit();

                        editor.putString("token", token);
                        editor.commit();

                    }
                });

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserToken", MODE_PRIVATE);
        String userTokenn = sharedPreferences1.getString("userToken","");

      /*  SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("UserToken1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.putString("userTokenn", userTokenn);
        editor.commit();


       */

     /*   if(userTokenn != null) {
            Intent intent = new Intent(MainActivity.this, EklenenProgramlar.class);
            startActivity(intent);
            finish();
        }

      */



    }

    public void kayitOl(View view) {
        Intent intent = new Intent(MainActivity.this, kayitOl.class);
        startActivity(intent);
        finish();
    }

    public void girisYap(View view)  {




     /*   OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                   //     .addHeader("Content-Type", "application/json")
                      //  .addHeader("Authorization",  "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImJlcmF0NUBnbWFpbC5jb20iLCJpYXQiOjE2MDE2NDYwNTAsImV4cCI6MTYwMTY1MzI1MH0.-7pXZWb64FtbrWjSxiBiVUVEZh6vhCtDtwV7vgSeQQk")
                       .addHeader("Authorization", "Bearer " + userToken)
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





   /*    Interceptor interceptor = new Interceptor() {
           @NotNull
           @Override
           public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
               Request newRequest = chain.request().newBuilder()
                       .addHeader("Content-Type", "application/json")
                       .addHeader("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImJlbjFAZ21haWwuY29tIiwiaWF0IjoxNjAxNTc0NTIzLCJleHAiOjE2MDE1ODE3MjN9.1rkctpOVYlLiJ08ceMHMq7-QVYcY7eqggN0wNtbd_A4" )
                       .build();
               return chain.proceed(newRequest);
           }
       };
       OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
       okHttpBuilder.addInterceptor(interceptor);
       OkHttpClient okHttpClient = okHttpBuilder.build();

    */




       HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

      // OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                //.client(okHttpClient)
                .build();

        GirisYapAPIInterface girisYapAPIInterface = retrofit.create(GirisYapAPIInterface.class);

     Call<GirisYapModel> call = girisYapAPIInterface.at(girisYapEmail.getText().toString(),girisYapPassword.getText().toString());

     // "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImJlbjJAZ21haWwuY29tIiwiaWF0IjoxNjAxNTg2MDMzLCJleHAiOjE2MDE1OTMyMzN9.dNbs8FKVDe-fwn34oKgCY90-IdVE7S0TbV_ACzWlbro",


        call.enqueue(new Callback<GirisYapModel>() {
            @Override
            public void onResponse(@NotNull Call<GirisYapModel> call, Response<GirisYapModel> response) {


                Log.d("Response status ", response.toString());

                    if(response.body().status.equals("success") ) {

                        userToken = response.body().girisYapDataList.getToken();
                        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("UserToken", MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences2.edit();
                        editor1.putString("userToken", userToken);
                        editor1.commit();


                        Log.d("UserToken:  " , response.body().girisYapDataList.getToken());
                        Log.d("PhoneToken: ", response.body().girisYapDataList.getPhoneToken());



                         id = response.body().girisYapDataList.getUserId();

                        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id", id);
                        editor.commit();

                        Log.d("User Id ++", response.body().girisYapDataList.getUserId().toString());


                        //hesap açık kalması için

                     /*  logged = response.body().girisYapDataList.getToken();

                        SharedPreferences sharedPreferences3 = getApplicationContext().getSharedPreferences("Logged", MODE_PRIVATE);
                        SharedPreferences.Editor editor3 = sharedPreferences3.edit();

                        editor3.putString("logged", logged);
                        editor3.commit();

                      */

                        Toast.makeText(MainActivity.this, response.message(),Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, EklenenProgramlar.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Log.d("user11", response.body().status);
                    Log.d("user1", response.message());
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<GirisYapModel> call, Throwable t) {
                Log.d("user2",t.getMessage());

              Toast.makeText(MainActivity.this, t.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }
}