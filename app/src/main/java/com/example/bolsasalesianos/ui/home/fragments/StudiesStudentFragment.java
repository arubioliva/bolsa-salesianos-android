package com.example.bolsasalesianos.ui.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.adapters.StudyStudentAdapter;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.StudyStudent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudiesStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudiesStudentFragment extends BaseFragment {

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

    /*
     * Rellena los estudios del estudiante logeado a a partir del dni guardado en el sharedpreferences
     * */
    private void fillRecycler() {
        StudyStudent studyStudent = new StudyStudent();
        studyStudent.setStudent(getStudentSP().getString("dni", null));
        database.getServices().searchStudies(studyStudent).enqueue(new Callback<List<StudyStudent>>() {
            @Override
            public void onResponse(Call<List<StudyStudent>> call, Response<List<StudyStudent>> response) {
                RecyclerView recyclerView = getView().findViewById(R.id.studies_list);
                StudyStudentAdapter studyStudentAdapter = new StudyStudentAdapter(response.body());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(studyStudentAdapter);
            }

            @Override
            public void onFailure(Call<List<StudyStudent>> call, Throwable t) {

            }
        });
    }

    public StudiesStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudiesStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudiesStudentFragment newInstance(String param1, String param2) {
        StudiesStudentFragment fragment = new StudiesStudentFragment();
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
        return inflater.inflate(R.layout.fragment_studies_student, container, false);
    }
}