package com.example.bolsasalesianos.ui.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Credential;
import com.example.bolsasalesianos.pojos.Status;
import com.example.bolsasalesianos.pojos.Student;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private Database database;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new Database();
        initializeSharedPreferences();
        getView().findViewById(R.id.profile_save_data).setOnClickListener(this);
        fillUserForm();
    }

    @Override
    public void onClick(View view) {
        if (validForm()) {
            updateCredentialAndStudent();
        }
    }


    private void updateCredentialAndStudent() {
        Credential credential = createCredentialsFromForm();
        database.getServices().updateCredential(credential).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.body().getStatus() != 0) {
                    setTextViewError(R.id.profile_user, "Este nombre de usuario está ocupado.");
                } else {
                    updateStudent();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }
    /*
    * Actualiza un estudiante a partir de un formulario en la aplicacion.
    * */
    private void updateStudent() {
        Student student = createStudentFromForm();
        database.getServices().updateStudent(student).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    /*
     * Rellena el formulario del usuario con los datos actuales.
     * */
    public void fillUserForm() {
        Student student = new Student();
        student.setDni(getStudentSP().getString("dni", null));
        database.getServices().searchStudent(student).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Student student = response.body();
                if (student != null) {
                    setViewText(R.id.profile_dni, student.getDni());
                    setViewText(R.id.profile_name, student.getName());
                    setViewText(R.id.profile_last_name, student.getLastName());
                    setViewText(R.id.profile_phone, student.getPhone());
                    setViewText(R.id.profile_email, student.getEmail());
                    setViewText(R.id.profile_resume, student.getResume());
                    setCheckBox(R.id.profile_license_check, student.getLicense());
                    setCheckBox(R.id.profile_employed_check, student.getEmployed());
                    setCheckBox(R.id.profile_data_transfer_check, student.getDataTransf());
                    fillCredentials(student.getCredential());
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });
    }

    /*
     * Rellena los datos de los credenciales del estudiante.
     * */
    public void fillCredentials(String id) {
        Credential credential = new Credential();
        credential.setId(id);
        database.getServices().searchCredential(credential).enqueue(new Callback<Credential>() {
            @Override
            public void onResponse(Call<Credential> call, Response<Credential> response) {
                Credential credential = response.body();
                if (credential != null) {
                    setViewText(R.id.profile_user, credential.getUser());
                    setViewText(R.id.profile_pass, credential.getPass());
                    setViewText(R.id.profile_pass_2, credential.getPass());
                }
            }

            @Override
            public void onFailure(Call<Credential> call, Throwable t) {

            }
        });
    }

    /*
    * Comprueba que los nuevos datos guardados en el formulario sean correctos.
    * */
    public boolean validForm() {
        boolean correct = isValid(R.id.profile_dni, "^\\d{8}[a-zA-Z]$", "El dni no es válido.")
                & isValid(R.id.profile_name, "^((\\w|ñ){2,} *){1,4}$", "El nombre no es válido.")
                & isValid(R.id.profile_last_name, "^((\\w|ñ){2,} *){1,4}$", "Los apellidos no son válidos.")
                & isValid(R.id.profile_phone, "^([+](\\d{1,3}([-]\\d{1,3})?) ?)?\\d{9}$", "El número de teléfono no es valido.")
                & isValid(R.id.profile_email, "^\\w+@\\w+[.]\\w{2,3}$", "El email no es válido.")
                & isValid(R.id.profile_user, "^(\\w|ñ){2,}$", "El nombre de usuario no es válido.")
                & isValid(R.id.profile_pass, "^(?=.*([A-Z]|Ñ))(?=.*([a-z]|ñ))(?=.*[0-9])(?=.*[#?!@$ %^&*-]).{6,}$", "La contraseña debe contener mayusculas, minusculas simbolos y una longitud de 6 o más.");

        if (!getViewText(R.id.profile_pass).equals(getViewText(R.id.profile_pass_2))) {
            setTextViewError(R.id.profile_pass_2, "Las contraseñas no coinciden.");
            correct = false;
        }
        return correct;
    }

    /*
    * Crea el nuevo objeto de tipo credencial a partir del formulario.
    * */
    public Credential createCredentialsFromForm() {
        return new Credential(getViewText(R.id.profile_user), getViewText(R.id.profile_pass), "Estudiante", getLoginSP().getString("id", null), getDay());
    }

    public Student createStudentFromForm() {
        return new Student(getViewText(R.id.profile_dni), getViewText(R.id.profile_name),
                getViewText(R.id.profile_last_name), getViewText(R.id.profile_phone),
                getViewText(R.id.profile_email),getViewText(R.id.profile_resume), getCheckValue(R.id.profile_license_check),
                getCheckValue(R.id.profile_employed_check), getCheckValue(R.id.profile_data_transfer_check));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}