package com.beratucgul.at2.ProgramEkle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.beratucgul.at2.ApiData.ProgramCekData;
import com.beratucgul.at2.DersEkle.EklenilenDersler;
import com.beratucgul.at2.GunEkle.EklenilenGunler;
import com.beratucgul.at2.Interface.ProgramAPIInterface;
import com.beratucgul.at2.MainActivity;
import com.beratucgul.at2.Model.ProgramCekPostModel;
import com.beratucgul.at2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.WHITE;


public class EklenenProgramlar extends AppCompatActivity {

    public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";
    //public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";

    // public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";
    RecyclerView programRecyclerView;
    ArrayList<ProgramCekData> programCekDataArrayList;
    Retrofit retrofit;
    ProgramRecyclerAdapter programRecyclerAdapter;
    List<ProgramCekData> programCekData;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    public int id;
    String userToken;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eklenen_programlar);
        programRecyclerView = findViewById(R.id.programRecyclerView);


      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("AT");
     //  toolbar.setNavigationIcon(R.drawable.menubar);
       toolbar.setTitleTextColor(WHITE);
       toolbar.setTitleMarginTop(50);
       toolbar.setTitleMarginStart(650);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.menubar));




        SharedPreferences sharedPreferences2 = getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = sharedPreferences2.getString("userToken", "");




     /*   OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        //     .addHeader("Content-Type", "application/json")
                        .header("Authorization",  "Bearer " + userToken)
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


        SharedPreferences sharedPreferences3 = getSharedPreferences("Update2", MODE_PRIVATE);

        String lesson = sharedPreferences3.getString("ders", "");
//        String day = sharedPreferences3.getString("gun", "");
//        int day = sharedPreferences3.getInt("gun", 0);
        String absent = sharedPreferences3.getString("devamsızlık", "");
        String hour = sharedPreferences3.getString("saat", "");

        System.out.println("Güncellenen Programlar " + lesson + absent + hour);






        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        ProgramAPIInterface programAPIInterface = retrofit.create(ProgramAPIInterface.class);


        loadProgramData();





        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                programCekDataArrayList = programRecyclerAdapter.programCekDataArrayList;
                int position = viewHolder.getAdapterPosition();

                Call<ProgramSil> call = programAPIInterface.deleteProgram(programCekDataArrayList.get(position).getProgramId());
                call.enqueue(new Callback<ProgramSil>() {
                    @Override
                    public void onResponse(Call<ProgramSil> call, Response<ProgramSil> response) {
                        programCekDataArrayList = programRecyclerAdapter.programCekDataArrayList;
                        programCekDataArrayList.remove(viewHolder.getAdapterPosition());
                        programRecyclerAdapter.notifyDataSetChanged();

                        Toast.makeText(EklenenProgramlar.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ProgramSil> call, Throwable t) {

                    }
                });

            }
        });
        itemTouchHelper.attachToRecyclerView(programRecyclerView);

        //***************************************************

            ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                programCekDataArrayList = programRecyclerAdapter.programCekDataArrayList;
                int position = viewHolder.getAdapterPosition();


                ProgramPutAPIInterface programPutAPIInterface = retrofit.create(ProgramPutAPIInterface.class);

                Call<ProgramPut> call = programPutAPIInterface.updateProgramId(programCekDataArrayList.get(position).getProgramId());

                call.enqueue(new Callback<ProgramPut>() {
                    @Override
                    public void onResponse(Call<ProgramPut> call, Response<ProgramPut> response) {

                        int a = programCekDataArrayList.get(position).getProgramId();

                        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("Update1", MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences2.edit();

                        editor1.putInt("ProgramId", a);
                        editor1.commit();


                        String lesson = programCekDataArrayList.get(position).lessonId;
                        String day = programCekDataArrayList.get(position).dayId;
                       // int day = programCekDataArrayList.get(position).dayId;
                        String absent = programCekDataArrayList.get(position).absent;
                        String hour = programCekDataArrayList.get(position).hour;




                        sharedPreferences1 = getApplicationContext().getSharedPreferences("Update", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences1.edit();

                        editor.putString("lesson", lesson);
                       // editor.putString("day", day);
                        editor.putString("day", day);
                        editor.putString("absent", absent);
                        editor.putString("hour", hour);

                        editor.commit();


                     //   System.out.println("Program User: " + b);

                        System.out.println("Program id  " + a);

                        Intent intent = new Intent(EklenenProgramlar.this, ProgramGuncelle.class);

                        startActivity(intent);

                      //  Toast.makeText(EklenenProgramlar.this, response.message(), Toast.LENGTH_SHORT).show();
                        Log.d("Swiped right Success", response.message());
                    }

                    @Override
                    public void onFailure(Call<ProgramPut> call, Throwable t) {
                        Log.d("Swiped Right Fail ", t.getMessage());
                    }
                });
            }
        });
        itemTouchHelper1.attachToRecyclerView(programRecyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();

    /*    Intent intent = getIntent();
        intent.getStringExtra("program1");
        intent.getStringExtra("program2");
        intent.getStringExtra("program3");
        intent.getStringExtra("program4");
    //    intent.getIntExtra("id", 0);

     */

    }

    public void goToEklenecekProgramlar(View view) {

        Intent intentToProgramEkle = new Intent(EklenenProgramlar.this, ProgramEkle.class);

        startActivity(intentToProgramEkle);

    }
    private void loadProgramData() {

        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);

        SharedPreferences sharedPreferences2 = getSharedPreferences("Token", MODE_PRIVATE);
        String phoneToken = sharedPreferences2.getString("token","");



        ProgramAPIInterface programAPIInterface = retrofit.create(ProgramAPIInterface.class);
        Call<ProgramCekPostModel> call = programAPIInterface.getProgramCekData(id);

        call.enqueue(new Callback<ProgramCekPostModel>() {
            @Override
            public void onResponse(Call<ProgramCekPostModel> call, Response<ProgramCekPostModel> response) {

                Log.d("Get Program Success before: " , response.message());

                if(response.isSuccessful()) {
                    if(response.body().status.equals("Success")) {

                      //  ProgramCekPostModel programCekData = response.body();
                       // programCekDataArrayList = new ArrayList<>(programCekData);

                        programCekData = response.body().getProgramCekDataList();
                        programCekDataArrayList = new ArrayList<>(programCekData);

                        //RecyclerView
                        programRecyclerView.setLayoutManager(new LinearLayoutManager(EklenenProgramlar.this));
                        programRecyclerAdapter = new ProgramRecyclerAdapter(programCekDataArrayList);
                        programRecyclerView.setAdapter(programRecyclerAdapter);



                        Log.d("Get Program Success", response.message());

                    }
                }
            }

            @Override
            public void onFailure(Call<ProgramCekPostModel> call, Throwable t) {

                Log.d("Get Program Fail: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
          /*  case R.id.lessons:
                Intent intent = new Intent(EklenenProgramlar.this, EklenilenDersler.class);
            startActivity(intent);
            break;

           */
           case R.id.days:
                Intent intentToDays = new Intent(EklenenProgramlar.this, EklenilenGunler.class);
                intentToDays.putExtra("data", "newest");
                startActivity(intentToDays);
                break;
            case R.id.logout:


                SharedPreferences sharedPreferences3 = getSharedPreferences("UserToken", MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                editor3.clear();
                Intent intentToEklenenProgramlar = new Intent(EklenenProgramlar.this, MainActivity.class);

                startActivity(intentToEklenenProgramlar);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}