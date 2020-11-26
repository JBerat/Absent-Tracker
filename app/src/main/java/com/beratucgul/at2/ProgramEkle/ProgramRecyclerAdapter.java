package com.beratucgul.at2.ProgramEkle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beratucgul.at2.ApiData.DersCekData;
import com.beratucgul.at2.ApiData.ProgramCekData;
import com.beratucgul.at2.R;

import java.util.ArrayList;
import java.util.List;

public class ProgramRecyclerAdapter extends RecyclerView.Adapter<ProgramRecyclerAdapter.RowHolder> {

     ArrayList<ProgramCekData> programCekDataArrayList;
     ArrayList<TextView> programPutArrayList;




    public ProgramRecyclerAdapter(ArrayList<ProgramCekData> programCekDataArrayList) {
        this.programCekDataArrayList = programCekDataArrayList;
    }

    @NonNull
    @Override
    public ProgramRecyclerAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_program, parent , false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramRecyclerAdapter.RowHolder holder, int position) {
        holder.bind(programCekDataArrayList.get(position));

    }

    @Override
    public int getItemCount() {
       return programCekDataArrayList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{

        TextView dersId, dayId, absentId, hourId;
        ImageView dersProgram,gunProgram,saatProgram,devamsızlıkProgram;


        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void bind(ProgramCekData programCekData) {
            dersId = itemView.findViewById(R.id.dersId);
            dayId = itemView.findViewById(R.id.dayId);
            absentId = itemView.findViewById(R.id.absentId);
            hourId = itemView.findViewById(R.id.hourId);

            dersId.setText(programCekData.lessonId);
            dayId.setText(programCekData.dayId);
            absentId.setText(programCekData.absent);
            hourId.setText(programCekData.hour);
        }
    }
}
