package com.example.bolsasalesianos.ui.home.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Idiom;
import com.example.bolsasalesianos.pojos.IdiomStudent;
import com.example.bolsasalesianos.pojos.Status;
import com.example.bolsasalesianos.pojos.Student;
import com.example.bolsasalesianos.pojos.Study;
import com.example.bolsasalesianos.pojos.StudyStudent;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStudiesFragment extends BaseFragment implements View.OnClickListener {
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        initializeSharedPreferences();
        getView().findViewById(R.id.save_study_btn).setOnClickListener(this);
        getView().findViewById(R.id.save_idiom_btn).setOnClickListener(this);
        getView().findViewById(R.id.remove_study_btn).setOnClickListener(this);
        getView().findViewById(R.id.remove_idiom_btn).setOnClickListener(this);

        initYearsSpinners();
        initStudiesSpinner();
        inintIdiomsSpinner();
        initIdiomsStudentSpinner();
        initStudiesStudentSpinner();

    }

    private void initStudiesStudentSpinner() {
        StudyStudent studyStudent = new StudyStudent();
        studyStudent.setStudent(getStudentSP().getString("dni", null));
        database.getServices().searchStudies(studyStudent).enqueue(new Callback<List<StudyStudent>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<StudyStudent>> call, Response<List<StudyStudent>> response) {
                List<String> studies = response.body().stream().map(StudyStudent::getStudy).collect(Collectors.toList());
                ArrayAdapter<String> idiomsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, studies);
                Spinner idiomsSpin = getView().findViewById(R.id.student_study_sel);
                idiomsSpin.setAdapter(idiomsAdapter);
            }

            @Override
            public void onFailure(Call<List<StudyStudent>> call, Throwable t) {

            }
        });
    }

    private void initIdiomsStudentSpinner() {
        Student student = new Student();
        student.setDni(getStudentSP().getString("dni", null));
        database.getServices().searchIdiomsByStudent(student).enqueue(new Callback<List<IdiomStudent>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<IdiomStudent>> call, Response<List<IdiomStudent>> response) {
                if (response.body()!=null) {
                    List<String> idioms = response.body().stream().map(o -> o.getLanguage() + "-" + o.getLevel()).collect(Collectors.toList());
                    ArrayAdapter<String> idiomsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, idioms);
                    Spinner idiomsSpin = getView().findViewById(R.id.student_idiom_sel);
                    idiomsSpin.setAdapter(idiomsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<IdiomStudent>> call, Throwable t) {

            }
        });
    }

    private void inintIdiomsSpinner() {
        database.getServices().getIdioms(new Idiom()).enqueue(new Callback<List<Idiom>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Idiom>> call, Response<List<Idiom>> response) {
                System.out.println(response.body());
                List<String> idioms = response.body().stream().map(Idiom::getLanguage).collect(Collectors.toList());
                ArrayAdapter<String> idiomsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, idioms);
                Spinner idiomsSpin = getView().findViewById(R.id.idiom_language);
                idiomsSpin.setAdapter(idiomsAdapter);

                List<String> idiomLevels = response.body().stream().map(Idiom::getLevel).collect(Collectors.toList());
                Set<String> set = new LinkedHashSet<>(idiomLevels);
                idiomLevels = new ArrayList<>(set);

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
        for (int i = 1990; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);

        Spinner startSpin = getView().findViewById(R.id.start_year);
        startSpin.setAdapter(adapter);

        Spinner endSpin = getView().findViewById(R.id.end_year);
        endSpin.setAdapter(adapter);
    }

    public String getSpinnerValue(int id) {
        return ((Spinner) getView().findViewById(id)).getSelectedItem().toString();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save_study_btn) {
            saveStudies();
        } else if (view.getId() == R.id.save_idiom_btn) {
            saveIdiom();
        } else if (view.getId() == R.id.remove_idiom_btn) {
            removeIdiom();
        } else if (view.getId() == R.id.remove_study_btn) {
            removeStudy();
        }
    }

    private void removeStudy() {
        StudyStudent studyStudent = new StudyStudent();
        studyStudent.setStudent(getStudentSP().getString("dni", null));
        studyStudent.setStudy(getSpinnerValue(R.id.student_study_sel));
        database.getServices().removeStudies(studyStudent).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(getContext(), "Estudio eliminado correctamente.", Toast.LENGTH_SHORT).show();
                    initStudiesStudentSpinner();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    private void removeIdiom() {
        String[] idiomValues = getSpinnerValue(R.id.student_idiom_sel).split("-");
        Idiom idiom = new Idiom();
        idiom.setLanguage(idiomValues[0]);
        idiom.setLevel(idiomValues[1]);
        database.getServices().getIdiom(idiom).enqueue(new Callback<Idiom>() {
            @Override
            public void onResponse(Call<Idiom> call, Response<Idiom> response) {
                if (response.body() != null) {
                    IdiomStudent idiomStudent = new IdiomStudent();
                    idiomStudent.setStudent(getStudentSP().getString("dni", null));
                    idiomStudent.setIdiom(response.body().getId().toString());
                    database.getServices().removeIdiomStudent(idiomStudent).enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.body().getStatus() == 0){
                                Toast.makeText(getContext(), "Idioma eliminado correctamente.", Toast.LENGTH_SHORT).show();
                                initIdiomsStudentSpinner();
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Idiom> call, Throwable t) {

            }
        });

    }

    public void saveIdiom() {

        Idiom idiom = new Idiom();
        idiom.setLanguage(getSpinnerValue(R.id.idiom_language));
        idiom.setLevel(getSpinnerValue(R.id.idiom_level));

        database.getServices().getIdiom(idiom).enqueue(new Callback<Idiom>() {
            @Override
            public void onResponse(Call<Idiom> call, Response<Idiom> response) {
                if (response.body() != null) {
                    IdiomStudent idiomStudent = new IdiomStudent();
                    idiomStudent.setStudent(getStudentSP().getString("dni", null));
                    idiomStudent.setIdiom(response.body().getId().toString());
                    database.getServices().getIdiomsStudent(idiomStudent).enqueue(new Callback<List<IdiomStudent>>() {
                        @Override
                        public void onResponse(Call<List<IdiomStudent>> call, Response<List<IdiomStudent>> response) {
                            if (response.body().size() == 0) {
                                database.getServices().newIdiomStudent(idiomStudent).enqueue(new Callback<Status>() {
                                    @Override
                                    public void onResponse(Call<Status> call, Response<Status> response) {
                                        if (response.body().getStatus() == 0) {
                                            Toast.makeText(getContext(), "Idioma guardado correctamente.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Status> call, Throwable t) {

                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Este idioma ya fue guardado anteriormente.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<IdiomStudent>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Idiom> call, Throwable t) {

            }
        });
    }

    public void saveStudies() {
        int start = Integer.parseInt(getSpinnerValue(R.id.start_year));
        int end = Integer.parseInt(getSpinnerValue(R.id.end_year));
        if (start < end) {
            StudyStudent studyStudent = new StudyStudent();
            studyStudent.setStudent(getStudentSP().getString("dni", null));
            studyStudent.setStudy(getSpinnerValue(R.id.study_selector));
            database.getServices().searchStudies(studyStudent).enqueue(new Callback<List<StudyStudent>>() {
                @Override
                public void onResponse(Call<List<StudyStudent>> call, Response<List<StudyStudent>> response) {
                    if (response.body().size() == 0) {
                        studyStudent.setStart(start + "");
                        studyStudent.setEnd(end + "");
                        database.getServices().newStudy(studyStudent).enqueue(new Callback<Status>() {
                            @Override
                            public void onResponse(Call<Status> call, Response<Status> response) {
                                if (response.body().getStatus() == 0) {
                                    Toast.makeText(getContext(), "Estudios guardados correctamente.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Status> call, Throwable t) {

                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Usted ya guardó los estudios indicados.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<StudyStudent>> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(), "El año de inicio no puede ser igual o superior al año de fin.", Toast.LENGTH_SHORT).show();
        }

    }
}