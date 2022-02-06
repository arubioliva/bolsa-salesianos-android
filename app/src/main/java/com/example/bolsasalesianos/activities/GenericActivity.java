package com.example.bolsasalesianos.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Credential;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
* Actividad donde guardo metodos que necesito reutilizar en varias actividades diferentes.
* */
public class GenericActivity extends AppCompatActivity {
    private SharedPreferences loginSP;
    private SharedPreferences studentSP;
    /*
    * Inicializa los sharedprefereces de login y de estudiante para acceder a los datos guardados
    * anteriormente.
    * */
    public void initializeSharedPreferences() {
        setLoginSP(getSharedPreferences("Login", MODE_PRIVATE));
        setStudentSP(getSharedPreferences("Student", MODE_PRIVATE));
    }

    /*
    * Inicia una nueva actividad cerrando la anterior.
    * */
    public void startActivityAndFinishActually(Context context, Class<?> cls) {
        finishAndRemoveTask();
        Intent newIntent = new Intent(context, cls);
        startActivity(newIntent);
    }

    /*
    * Devuelve el día actual con el formato específico de la base de datos
    * */
    public String getDay(){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    /*
    * Guarda la ultima conexion del credencial especificado.
    * */
    public void setLastConnection(Credential credential){
        String today = getDay();
        Database database = new Database();
        credential.setLastConnection(today);
    }

    /*
     * Devuelve el valor de un textview especificado por parametro
     * */
    public String getViewText(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }

    /*
     * Introduce el texto a un textview.
     * */
    public void setViewText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

    /*
     * Pone un error a un textview.
     * */
    public void setTextViewError(int id, String error) {
        ((TextView) findViewById(id)).setError(error);
    }

    /*
     * Pone un error a un textview.
     * */
    public void initializeButtons(List<Integer> ids, View.OnClickListener onClickListener) {
        for (Integer id : ids) {
            initializeButton(id, onClickListener);
        }
    }

    /*
     * Inicializa un boton indicado por parametro.
     * */
    public void initializeButton(Integer id, View.OnClickListener onClickListener) {
        Button button = findViewById(id);
        button.setOnClickListener(onClickListener);
    }

    public SharedPreferences getLoginSP() {
        return loginSP;
    }

    public void setLoginSP(SharedPreferences loginSP) {
        this.loginSP = loginSP;
    }

    public SharedPreferences getStudentSP() {
        return studentSP;
    }

    public void setStudentSP(SharedPreferences studentSP) {
        this.studentSP = studentSP;
    }

}

