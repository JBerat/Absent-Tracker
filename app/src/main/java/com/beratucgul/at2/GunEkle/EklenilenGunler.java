package com.beratucgul.at2.GunEkle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beratucgul.at2.ApiData.DersCekData;
import com.beratucgul.at2.ApiData.GunCekData;
import com.beratucgul.at2.DersEkle.FeedRecyclerAdapter;
import com.beratucgul.at2.Interface.GunAPIInterface;
import com.beratucgul.at2.ProgramEkle.EklenenProgramlar;
import com.beratucgul.at2.ProgramEkle.ProgramEkle;
import com.beratucgul.at2.ProgramEkle.ProgramGuncelle;
import com.beratucgul.at2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EklenilenGunler extends AppCompatActivity {

    ImageView BackToGunEkle;
    Retrofit retrofit;
     public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";
   //public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";
   // public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";
    GunRecyclerAdapter gunRecyclerAdapter;
    RecyclerView gunRecyclerView;
    ArrayList<GunCekData> gunCekDataArrayList;
    String userToken;
    ImageView goBack;
    TextView dersInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eklenilen_gunler);
        BackToGunEkle = findViewById(R.id.BackToGunEkle);
        gunRecyclerView = findViewById(R.id.gunRecyclerView);
        goBack = findViewById(R.id.goBack);
        dersInfo = findViewById(R.id.dersInfo);













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

        GunAPIInterface gunAPIInterface = retrofit.create(GunAPIInterface.class);


        loadGunData();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                gunCekDataArrayList = gunRecyclerAdapter.gunCekDataArrayList;
                int position = viewHolder.getAdapterPosition();

            /*    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("dayId", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("dayid", position);
                editor.commit();


                Intent intent = new Intent(EklenilenGunler.this, ProgramEkle.class);
                startActivity(intent);


             */
                Intent intentData = getIntent();
                String data =intentData.getStringExtra("data");

                if(data.matches("new") || data.matches("newest")) {

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("dayId", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("dayid", position);
                    editor.commit();

                    Intent intent = new Intent(EklenilenGunler.this, ProgramEkle.class);
                    startActivity(intent);

                } else   {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("dayId", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("dayid", position);
                    editor.commit();




                    Intent intent = new Intent(EklenilenGunler.this, ProgramGuncelle.class);
                    startActivity(intent);

                }




                System.out.println("Day Position id: " + position);

            }
        }));
        itemTouchHelper.attachToRecyclerView(gunRecyclerView);


     /*   ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                gunCekDataArrayList = gunRecyclerAdapter.gunCekDataArrayList;
                int position = viewHolder.getAdapterPosition();


                Call<GunSil> call = gunAPIInterface.deleteGun(gunCekDataArrayList.get(position).getDayId());
                call.enqueue(new Callback<GunSil>() {
                    @Override
                    public void onResponse(Call<GunSil> call, Response<GunSil> response) {
                        gunCekDataArrayList = gunRecyclerAdapter.gunCekDataArrayList;
                        gunCekDataArrayList.remove(viewHolder.getAdapterPosition());
                        gunRecyclerAdapter.notifyDataSetChanged();

                        Toast.makeText(EklenilenGunler.this, response.message(), Toast.LENGTH_SHORT).show();
                        Log.d("swipe success", response.message());
                    }

                    @Override
                    public void onFailure(Call<GunSil> call, Throwable t) {

                    }
                });

            }
        });
        itemTouchHelper.attachToRecyclerView(gunRecyclerView);
        */


    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        intent.getStringExtra("day");

    }

    public void goBackToGunEkle(View view ) {

        Intent intent = new Intent(EklenilenGunler.this, EklenenProgramlar.class);

        startActivity(intent);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private void loadGunData() {
        GunAPIInterface gunAPIInterface = retrofit.create(GunAPIInterface.class);

        Call<List<GunCekData>> call = gunAPIInterface.getGunCekData();
        call.enqueue(new Callback<List<GunCekData>>() {
            @Override
            public void onResponse(Call<List<GunCekData>> call, Response<List<GunCekData>> response) {
                if(response.isSuccessful()) {

                    List<GunCekData> gunCekDataList = response.body();
                    gunCekDataArrayList = new ArrayList<>(gunCekDataList);

                    gunRecyclerView.setLayoutManager(new LinearLayoutManager(EklenilenGunler.this));
                    gunRecyclerAdapter = new GunRecyclerAdapter(gunCekDataArrayList);
                    gunRecyclerView.setAdapter(gunRecyclerAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<GunCekData>> call, Throwable t) {
                Log.d("fail", t.getMessage());
            }
        });
    }

    public void goBack(View view) {

        Intent intent = new Intent(EklenilenGunler.this, EklenenProgramlar.class);
        startActivity(intent);
    }
}