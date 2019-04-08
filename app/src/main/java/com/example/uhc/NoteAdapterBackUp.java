package com.example.uhc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapterBackUp extends FirestoreRecyclerAdapter<productAD, NoteAdapterBackUp.NoteHolder> {


    public NoteAdapterBackUp(@NonNull FirestoreRecyclerOptions<productAD> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull productAD model) {

        holder.textViewCounty.setText(model.getCounty());
        holder.textViewHospital.setText(model.getHospital());
        //holder.textViewSerial.setText(model.toString());
        holder.textViewSerial.setText(String.valueOf(model.getSerial()));
        holder.textViewDepartment.setText(model.getDepartment());
        holder.textViewComment.setText(model.getComment());
        holder.textViewDate.setText(model.getDate());

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View V = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup,false);

        return new NoteHolder(V);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView textViewCounty;
        TextView textViewHospital;
        TextView textViewSerial;
        TextView textViewDepartment;
        TextView textViewComment;
        TextView textViewDate;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewCounty = itemView.findViewById(R.id.textView_County);
            textViewSerial = itemView.findViewById(R.id.textView_Serial);
            textViewHospital = itemView.findViewById(R.id.textView_Hospital);
            textViewDepartment = itemView.findViewById(R.id.textView_Department);
            textViewComment = itemView.findViewById(R.id.textView_Comment);
            textViewDate = itemView.findViewById(R.id.textView_Date);

        }
    }
}
