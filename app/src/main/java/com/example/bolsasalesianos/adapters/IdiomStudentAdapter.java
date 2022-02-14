package com.example.bolsasalesianos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.pojos.IdiomStudent;

import java.util.List;

public class IdiomStudentAdapter extends RecyclerView.Adapter<IdiomStudentAdapter.ViewHolder> {
    private final List<IdiomStudent> idiomStudents;

    public IdiomStudentAdapter(List<IdiomStudent> idiomStudents) {
        this.idiomStudents = idiomStudents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_idiom, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IdiomStudent idiomStudent = idiomStudents.get(position);
        holder.idiom.setText(idiomStudent.getLanguage());
        holder.level.setText(idiomStudent.getLevel());
    }

    @Override
    public int getItemCount() {
        return idiomStudents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idiom;
        private TextView level;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idiom = itemView.findViewById(R.id.idiomStudent);
            level = itemView.findViewById(R.id.level);
        }
    }
}
