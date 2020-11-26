package com.beratucgul.at2.DersEkle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beratucgul.at2.ApiData.DersCekData;
import com.beratucgul.at2.R;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.RowHolder> {

     ArrayList<DersCekData> dersCekDataList;
     Integer id;
     Retrofit retrofit;
     FeedRecyclerAdapter feedRecyclerAdapter;
     RecyclerView dersRecyclerView;


    public FeedRecyclerAdapter(ArrayList<DersCekData> dersCekDataList) {
        this.dersCekDataList = dersCekDataList;

    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new RowHolder(view);
    }


    public void removeItem(int position) {
        dersCekDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dersCekDataList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(dersCekDataList.get(position));

    }

    @Override
    public int getItemCount() {
        return dersCekDataList.size();
    }
    // LayoutInflater inflater;

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView dersEkle;
        ImageView silImage;



        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void bind(DersCekData dersCekData) {
            dersEkle = itemView.findViewById(R.id.dersEkle);
         //   silImage = itemView.findViewById(R.id.silImage);

            dersEkle.setText(dersCekData.lesson);
        //    silImage.setImageResource(R.drawable.sil);
        }



    }





}
