package com.example.bolsasalesianos.ui.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.adapters.VacantsAdapter;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.databinding.FragmentHomeBinding;
import com.example.bolsasalesianos.pojos.Student;
import com.example.bolsasalesianos.pojos.Vacant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding binding;
    private Database database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSharedPreferences();
        database = new Database();
        fillRecycler();
    }

    private void fillRecycler() {
        Student student = new Student();
        student.setDni(getStudentSP().getString("dni", null));
        database.getServices().getVacants(student).enqueue(new Callback<List<Vacant>>() {
            @Override
            public void onResponse(Call<List<Vacant>> call, Response<List<Vacant>> response) {
                if (response.body().size() != 0) {
                    RecyclerView recyclerView = getView().findViewById(R.id.vacants_list);
                    VacantsAdapter vacantsAdapter = new VacantsAdapter(response.body(), student.getDni());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(vacantsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Vacant>> call, Throwable t) {

            }
        });
    }
}