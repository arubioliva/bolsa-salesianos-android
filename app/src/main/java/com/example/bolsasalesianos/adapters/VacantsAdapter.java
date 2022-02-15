package com.example.bolsasalesianos.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Selection;
import com.example.bolsasalesianos.pojos.Status;
import com.example.bolsasalesianos.pojos.Vacant;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacantsAdapter extends RecyclerView.Adapter<VacantsAdapter.ViewHolder> {

    private final List<Vacant> vacants;
    private final String dni;

    public VacantsAdapter(List<Vacant> vacants, String dni) {
        this.vacants = vacants;
        this.dni = dni;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VacantsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_vacant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vacant vacant = vacants.get(position);
        holder.vacantId = vacant.getId();
        holder.workstation.setText(vacant.getWorkstation());
        holder.vehicle.setText(vacant.getVehicle() == 1 ? "Necesario" : "No necesario");
        holder.workExperience.setText(vacant.getWorkExperience() == 1 ? "Necesaria" : "No necesaria");
        holder.salary.setText(vacant.getSalary() + "");
        holder.study.setText(vacant.getStudy());


    }

    @Override
    public int getItemCount() {
        return vacants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int vacantId;
        public TextView workstation;
        public TextView vehicle;
        public TextView workExperience;
        public TextView salary;
        public TextView study;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            workstation = itemView.findViewById(R.id.language_value);
            vehicle = itemView.findViewById(R.id.vehicle_value);
            workExperience = itemView.findViewById(R.id.work_experience_value);
            salary = itemView.findViewById(R.id.salary_value);
            study = itemView.findViewById(R.id.study_value);


        }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
            alert.setTitle("Desea inscribirse en esta vacante?");

            alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Database database = new Database();
                    Selection selection = new Selection();
                    selection.setVacant(vacantId);
                    selection.setStudent(dni);

                    database.getServices().getSelection(selection).enqueue(new Callback<List<Selection>>() {
                        @Override
                        public void onResponse(Call<List<Selection>> call, Response<List<Selection>> response) {
                            if (response.body().size() == 0) {
                                selection.setSelected(0);
                                selection.setDate(getDay());
                                database.getServices().newSelection(selection).enqueue(new Callback<Status>() {
                                    @Override
                                    public void onResponse(Call<Status> call, Response<Status> response) {
                                        if (response.body().getStatus() == 0) {
                                            Toast.makeText(itemView.getContext(), "Se ha suscrito a la oferta.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Status> call, Throwable t) {

                                    }
                                });
                            } else {
                                Toast.makeText(itemView.getContext(), "Usted ya estaba suscrito a esta oferta.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Selection>> call, Throwable t) {

                        }
                    });

                }
            });
            alert.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
            alert.show();
        }

        public String getDay() {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(new Date());
        }
    }
}
