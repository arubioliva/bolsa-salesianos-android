package com.example.bolsasalesianos.ui.home.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Credential;
import com.example.bolsasalesianos.pojos.Status;
import com.example.bolsasalesianos.pojos.Student;
import com.example.bolsasalesianos.ui.home.fragments.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentRegisterFragment extends BaseFragment implements View.OnClickListener {

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
        getView().findViewById(R.id.register_student_button).setOnClickListener(this);
        database = new Database();
    }

    /*
    * Valida el formulario del estudiante y muestra el error correspondiente.
    * */
    public boolean validForm() {
        boolean correct = isValid(R.id.register_dni, "^\\d{8}[a-zA-Z]$", "El dni no es válido.")
                & isValid(R.id.register_name, "^((\\w|ñ){2,} *){1,4}$", "El nombre no es válido.")
                & isValid(R.id.register_last_name, "^((\\w|ñ){2,} *){1,4}$", "Los apellidos no son válidos.")
                & isValid(R.id.register_phone, "^([+](\\d{1,3}([-]\\d{1,3})?) ?)?\\d{9}$", "El número de teléfono no es valido.")
                & isValid(R.id.register_email, "^\\w+@\\w+[.]\\w{2,3}$", "El email no es válido.")
                & isValid(R.id.register_user, "^(\\w|ñ){2,}$", "El nombre de usuario no es válido.")
                & isValid(R.id.register_pass, "^(?=.*([A-Z]|Ñ))(?=.*([a-z]|ñ))(?=.*[0-9])(?=.*[#?!@$ %^&*-]).{6,}$", "La contraseña debe contener mayusculas, minusculas simbolos y una longitud de 6 o más.");

        if (!getViewText(R.id.register_pass).equals(getViewText(R.id.register_pass_2))) {
            setTextViewError(R.id.register_pass_2, "Las contraseñas no coinciden.");
            correct = false;
        }
        return correct;
    }

    /*
     * Crea un estudiante a partir de un formulario.
     * */
    public Student createStudentFromForm() {
        return new Student(getViewText(R.id.register_dni), getViewText(R.id.register_name),
                getViewText(R.id.register_last_name), getViewText(R.id.register_phone),
                getViewText(R.id.register_email), getCheckValue(R.id.license_check),
                getCheckValue(R.id.employed_check), getCheckValue(R.id.data_transfer_check));
    }

    /*
     * Crea un credencial a partir de un formulario.
     * */
    public Credential createCredentialsFromForm() {
        return new Credential(getViewText(R.id.register_user), getViewText(R.id.register_pass), "0000-00-00", "Estudiante");
    }

    @Override
    public void onClick(View view) {
        if (validForm()) {
            Student student = new Student();
            student.setDni(getViewText(R.id.register_dni));
            database.getServices().searchStudent(student).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    Student student = response.body();
                    if (student == null) {
                        createUser();
                    } else {
                        setTextViewError(R.id.register_dni, "Este dni ya está registrado.");
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {

                }
            });
        }
    }

    /*
    * Inserta un usuario a la base de datos a partir del formulario.
    * */
    private void createUser() {
        Credential credential = createCredentialsFromForm();
        database.getServices().newCredential(credential).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                if (status.getStatus() == 0) {
                    searchCredentials(credential);
                } else {
                    setTextViewError(R.id.register_user, "Este nombre de usuario está ocupado.");
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }
    /*
    * Busca que los credenciales no hayan sido creados anteriormente.
    * */
    private void searchCredentials(Credential credential) {
        database.getServices().searchCredential(credential).enqueue(new Callback<Credential>() {
            @Override
            public void onResponse(Call<Credential> call, Response<Credential> response) {
                Credential newCredential = response.body();
                if (newCredential.getId() != null) {
                    Student student = createStudentFromForm();
                    student.setCredential(newCredential.getId());
                    createStudent(student);
                }
            }

            @Override
            public void onFailure(Call<Credential> call, Throwable t) {

            }
        });
    }
    /*
     * Inserta el estudiante a partir del formulario.
     * */
    private void createStudent(Student student) {
        database.getServices().newStudent(student).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                if (status.getStatus() == 0) {
                    Toast.makeText(getContext(), "Registro finalizado.", Toast.LENGTH_LONG).show();
                    getActivity().finishAndRemoveTask();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });

    }

    public StudentRegisterFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentRegisterFragment newInstance(String param1, String param2) {
        StudentRegisterFragment fragment = new StudentRegisterFragment();
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
        return inflater.inflate(R.layout.fragment_student_register, container, false);
    }

}