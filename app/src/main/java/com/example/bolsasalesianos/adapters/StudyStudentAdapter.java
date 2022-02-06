package com.example.bolsasalesianos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.pojos.StudyStudent;

import java.util.List;

public class StudyStudentAdapter extends RecyclerView.Adapter<StudyStudentAdapter.ViewHolder> {
    private final List<StudyStudent> studiesStudent;

    public StudyStudentAdapter(List<StudyStudent> studiesStudent) {
        this.studiesStudent = studiesStudent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_study, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudyStudent studyStudent = studiesStudent.get(position);
        holder.study.setText(studyStudent.getStudy());
        holder.date.setText(String.format("%s - %s", studyStudent.getStart(), studyStudent.getEnd()));
    }

    @Override
    public int getItemCount() {
        return studiesStudent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView study;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            study = itemView.findViewById(R.id.study);
            date = itemView.findViewById(R.id.date);
        }
    }
}
