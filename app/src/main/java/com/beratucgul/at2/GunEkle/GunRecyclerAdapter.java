package com.beratucgul.at2.GunEkle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beratucgul.at2.ApiData.GunCekData;
import com.beratucgul.at2.DersEkle.FeedRecyclerAdapter;
import com.beratucgul.at2.R;

import java.util.ArrayList;

public class GunRecyclerAdapter extends RecyclerView.Adapter<GunRecyclerAdapter.RowHolder> {

    ArrayList<GunCekData> gunCekDataArrayList;
    ArrayList<RecyclerView.ViewHolder> days;

    public GunRecyclerAdapter(ArrayList<GunCekData> gunCekDataArrayList) {
        this.gunCekDataArrayList = gunCekDataArrayList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_gun, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {

        holder.bind(gunCekDataArrayList.get(position));



    }

    @Override
    public int getItemCount() {
        return gunCekDataArrayList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView gunEkle;
        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }


        public void bind(GunCekData gunCekData) {
            gunEkle = itemView.findViewById(R.id.gunEkle);

            gunEkle.setText(gunCekData.day);
        }
    }
}
