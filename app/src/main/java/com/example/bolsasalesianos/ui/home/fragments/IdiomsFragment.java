package com.example.bolsasalesianos.ui.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.adapters.IdiomStudentAdapter;
import com.example.bolsasalesianos.adapters.StudyStudentAdapter;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Idiom;
import com.example.bolsasalesianos.pojos.Student;
import com.example.bolsasalesianos.pojos.StudyStudent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdiomsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdiomsFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Database database;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSharedPreferences();
        database = new Database();
        fillRecycler();
    }

    private void fillRecycler() {
        Student student = new Student();
        student.setDni(getStudentSP().getString("dni",null));
        database.getServices().searchIdiomsByStudent(student).enqueue(new Callback<List<Idiom>>() {
            @Override
            public void onResponse(Call<List<Idiom>> call, Response<List<Idiom>> response) {
                if (response.body() != null){
                    RecyclerView recyclerView = getView().findViewById(R.id.idioms_list);
                    IdiomStudentAdapter idiomStudentAdapter = new IdiomStudentAdapter(response.body());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(idiomStudentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Idiom>> call, Throwable t) {

            }
        });
    }

    public IdiomsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdiomsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdiomsFragment newInstance(String param1, String param2) {
        IdiomsFragment fragment = new IdiomsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idioms, container, false);
    }
}