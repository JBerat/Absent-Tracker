package com.beratucgul.at2.DersEkle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beratucgul.at2.ApiData.DersCekData;
import com.beratucgul.at2.Interface.DersCekAPIInferface;
import com.beratucgul.at2.Interface.DersSilAPIInterface;
import com.beratucgul.at2.ProgramEkle.EklenenProgramlar;
import com.beratucgul.at2.R;
import com.google.android.material.snackbar.Snackbar;
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

public class EklenilenDersler extends AppCompatActivity {

 /*   List<String> dersEkleList;
    List<Integer> silList;

  */
    RecyclerView dersRecycleView;
    ArrayList<DersCekData> dersCekDataArrayList;
    final List<DersCekData> lessons = new ArrayList<>();
    Retrofit retrofit;
   // public final String BASE_URL = "http://192.168.1.105:5000/api/v1/";
   public final String BASE_URL = "http://361bbcd8a4fa.ngrok.io/api/v1/";
   //public final String BASE_URL = "https://api-absent.herokuapp.com/api/v1/";
    FeedRecyclerAdapter feedRecyclerAdapter;
    DersCekData dersCekDatas;
    String userToken;




    @Override
      protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_eklenilen_dersler);




        SharedPreferences sharedPreferences2 = getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = sharedPreferences2.getString("userToken", "");

      dersRecycleView = findViewById(R.id.dersRecycerView);
      //   silImage = findViewById(R.id.silImage);

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

        DersCekAPIInferface dersCekAPIInferface = retrofit.create(DersCekAPIInferface.class);
        DersSilAPIInterface dersSilAPIInterface = retrofit.create(DersSilAPIInterface.class);

        Intent intent = getIntent();
        intent.getStringExtra("ders");

        loadData();


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dersCekDataArrayList = feedRecyclerAdapter.dersCekDataList;
                int position = viewHolder.getAdapterPosition();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("lessonId", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("lessonid", position);
                editor.commit();

            }
        });
        itemTouchHelper.attachToRecyclerView(dersRecycleView);



     /*   ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                dersCekDataArrayList = feedRecyclerAdapter.dersCekDataList;
                int position = viewHolder.getAdapterPosition();

                Call<DersSil> call = dersSilAPIInterface.deleteData(dersCekDataArrayList.get(position).getLessonId());

                call.enqueue(new Callback<DersSil>() {
                    @Override
                    public void onResponse(Call<DersSil> call, Response<DersSil> response) {

                        dersCekDataArrayList = feedRecyclerAdapter.dersCekDataList;
                       dersCekDataArrayList.remove(viewHolder.getAdapterPosition());
                        feedRecyclerAdapter.notifyDataSetChanged();


                        Log.d("swipe success", response.message());
                      //  System.out.println(response);

                    }

                    @Override
                    public void onFailure(Call<DersSil> call, Throwable t) {
                        Log.d("swipe unsuccess", t.getMessage());

                    }
                });


            }

        });
        helper.attachToRecyclerView(dersRecycleView);
        */



  }











    @Override
    protected void onResume() {
        super.onResume();


        Intent intent = getIntent();
        intent.getStringExtra("ders");



      //  loadData();




    }

   /* public void deleteData() {

       int a = dersCekDataArrayList.indexOf(dersCekData.id);



        DersSilAPIInterface dersSilAPIInterface = retrofit.create(DersSilAPIInterface.class);

        Call<DersSil> call = dersSilAPIInterface.deleteData(a);

        call.enqueue(new Callback<DersSil>() {
            @Override
            public void onResponse(Call<DersSil> call, Response<DersSil> response) {

                if(response.isSuccessful()) {
                    Log.d("delete succesful", response.message());
                } else {
                    Log.d("delete failed" , response.message());
                }


            }

            @Override
            public void onFailure(Call<DersSil> call, Throwable t) {
                Log.d("delete unsuccesful", t.getMessage());
            }
        });


    }

    */







    private void loadData() {

        DersCekAPIInferface dersCekAPIInferface = retrofit.create(DersCekAPIInferface.class);

        Call<List<DersCekData>> call = dersCekAPIInferface.getDersCekData();

        call.enqueue(new Callback<List<DersCekData>>() {
            @Override
            public void onResponse(Call<List<DersCekData>> call, Response<List<DersCekData>> response) {
                if(response.isSuccessful()) {



                    List<DersCekData>     dersCekDataList= response.body();
                    dersCekDataArrayList = new ArrayList<>(dersCekDataList);

                    //RecyclerView
                    dersRecycleView.setLayoutManager(new LinearLayoutManager(EklenilenDersler.this));
                    feedRecyclerAdapter = new FeedRecyclerAdapter(dersCekDataArrayList);
                    dersRecycleView.setAdapter(feedRecyclerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DersCekData>> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

   /* private void updateData() {

        DersCekData dersCekData = new DersCekData();

            dersCekData.lesson.toString();


        DersCekAPIInferface dersCekAPIInferface = retrofit.create(DersCekAPIInferface.class);

        Call<DersCekData> call = dersCekAPIInferface.putDersData(, dersCekData);

        call.enqueue(new Callback<DersCekData>() {
            @Override
            public void onResponse(Call<DersCekData> call, Response<DersCekData> response) {
                if(response.isSuccessful()) {



                }
            }

            @Override
            public void onFailure(Call<DersCekData> call, Throwable t) {

            }
        });
    }

    */


    public void dersEkleButton(View view) {
        Intent intentToDersEkle = new Intent(EklenilenDersler.this, DersEkle.class);
        startActivity(intentToDersEkle);
    }

    public void goBack(View view) {
        Intent intent = new Intent(EklenilenDersler.this, EklenenProgramlar.class);
        startActivity(intent);
    }


}