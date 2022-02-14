package com.example.bolsasalesianos.ui.home.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Idiom;
import com.example.bolsasalesianos.pojos.Study;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStudiesFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Database database;
    public AddStudiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStudiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStudiesFragment newInstance(String param1, String param2) {
        AddStudiesFragment fragment = new AddStudiesFragment();
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
        return inflater.inflate(R.layout.fragment_add_studies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new Database();
        initYearsSpinners();
        initStudiesSpinner();
        inintIdiomsSpinner();

    }

    private void inintIdiomsSpinner() {
        database.getServices().getIdioms(new Idiom()).enqueue(new Callback<List<Idiom>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Idiom>> call, Response<List<Idiom>> response) {
                List<String> idioms = response.body().stream().map(Idiom::getLanguage).collect(Collectors.toList());
                ArrayAdapter<String> idiomsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, idioms);
                Spinner idiomsSpin = getView().findViewById(R.id.idiom_language);
                idiomsSpin.setAdapter(idiomsAdapter);

                List<String> idiomLevels = response.body().stream().map(Idiom::getLevel).collect(Collectors.toList());
                ArrayAdapter<String> levelsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, idiomLevels);
                Spinner levelsSpin = getView().findViewById(R.id.idiom_level);
                levelsSpin.setAdapter(levelsAdapter);
            }

            @Override
            public void onFailure(Call<List<Idiom>> call, Throwable t) {

            }
        });
    }

    private void initStudiesSpinner() {
        database.getServices().getStudies(new Study()).enqueue(new Callback<List<Study>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Study>> call, Response<List<Study>> response) {
                List<String> studies = response.body().stream().map(Study::getName).collect(Collectors.toList());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, studies);

                Spinner studiesSpin = getView().findViewById(R.id.study_selector);
                studiesSpin.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Study>> call, Throwable t) {

            }
        });
    }

    private void initYearsSpinners() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);

        Spinner startSpin = getView().findViewById(R.id.start_age);
        startSpin.setAdapter(adapter);

        Spinner endSpin = getView().findViewById(R.id.end_age);
        endSpin.setAdapter(adapter);
    }
}