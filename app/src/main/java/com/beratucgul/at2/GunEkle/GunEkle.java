package com.beratucgul.at2.GunEkle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beratucgul.at2.DersEkle.DersEkle;
import com.beratucgul.at2.DersEkle.EklenilenDersler;
import com.beratucgul.at2.Interface.GunAPIInterface;
import com.beratucgul.at2.Model.GunEklePostModel;
import com.beratucgul.at2.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GunEkle extends AppCompatActivity {

   // public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";
   public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";
    //public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";
    ImageView BacktoEklenilenGunler;
    TextView Gunler;
    EditText gunEditText;
    String gunSec;
    private String array_spinner[];
    String userToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun_ekle);

       // gunEditText = findViewById(R.id.gunEditText);
        Gunler = findViewById(R.id.Gunler);
        BacktoEklenilenGunler = findViewById(R.id.BackToEklenilenGunler);

        array_spinner=new String[7];
        array_spinner[0]="Pazartesi";
        array_spinner[1]="Salı";
        array_spinner[2]="Çarşamba";
        array_spinner[3]="Perşembe";
        array_spinner[4]="Cuma";
        array_spinner[5]="Cumartesi";
        array_spinner[6]="Pazar";
        Spinner s = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);


    }

    public void goBackToEklenilenGunler(View view ) {

        gunEklePost();

        Intent intent = new Intent(GunEkle.this, EklenilenGunler.class);


        gunSec = gunEditText.getText().toString();
        intent.putExtra("day", gunSec);

        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


    }
    public void gunEklePost() {

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

        GunAPIInterface gunAPIInterface = retrofit.create(GunAPIInterface.class);

        Call<GunEklePostModel> gunEklePostModelCall = gunAPIInterface.at(gunEditText.getText().toString());

        gunEklePostModelCall.enqueue(new Callback<GunEklePostModel>() {
            @Override
            public void onResponse(Call<GunEklePostModel> call, Response<GunEklePostModel> response) {

                SharedPreferences days= getApplicationContext().getSharedPreferences("days", MODE_PRIVATE);
                SharedPreferences.Editor dayEditor = days.edit();

                String day = gunEditText.getText().toString();

                dayEditor.putString("day", day);
                dayEditor.commit();


                Toast.makeText(GunEkle.this,response.message(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<GunEklePostModel> call, Throwable t) {
                Toast.makeText(GunEkle.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}