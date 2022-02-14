package com.example.bolsasalesianos.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Credential;
import com.example.bolsasalesianos.pojos.Student;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogActivity extends GenericActivity implements View.OnClickListener {
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        database = new Database();
        initializeButtons(Arrays.asList(R.id.log_button_log, R.id.log_button_register), this);
        initializeSharedPreferences();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_button_log:
                searchUser();
                break;
            case R.id.log_button_register:
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }

    /*
     * Realiza la busqueda de un usuario para realizar el login.
     * */
    public void searchUser() {
        database.getServices().searchCredential(new Credential(
                getViewText(R.id.log_user_area))).enqueue(new Callback<Credential>() {
            @Override
            public void onResponse(Call<Credential> call, Response<Credential> response) {
                if (response.body() == null) {
                    setTextViewError(R.id.log_user_area, "El nombre de usuario es incorrecto.");
                } else if (!response.body().getPass().equals(getViewText(R.id.log_pass_area))) {
                    setTextViewError(R.id.log_pass_area, "Contraseña incorrecta.");
                } else {
                    setLastConnection(response.body());
                    saveUserCredentials(response.body());
                    if (response.body().getType().equals("Estudiante")) {
                        saveStudentCredentials(response.body().getId());
                    }
                    startActivityAndFinishActually(getApplicationContext(), MainActivity.class);
                }
            }

            @Override
            public void onFailure(Call<Credential> call, Throwable t) {
            }
        });
    }

    /*
     * Guarda los credenciales de un usuario en los shared preferences.
     * */
    private void saveUserCredentials(Credential credential) {
        SharedPreferences.Editor editor = getLoginSP().edit();
        editor.putString("user", credential.getUser());
        editor.putString("pass", credential.getPass());
        editor.putString("id", credential.getId());
        editor.apply();
    }

    /*
     * Guarda el dni de un estudiante en los shared preferences.
     * */
    private void saveStudentCredentials(String credentialID) {
        Student student = new Student();
        student.setCredential(credentialID);
        database.getServices().searchStudent(student).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Student student = response.body();
                if (student != null) {
                    SharedPreferences.Editor editor = getStudentSP().edit();
                    editor.putString("dni", student.getDni());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });
    }
}