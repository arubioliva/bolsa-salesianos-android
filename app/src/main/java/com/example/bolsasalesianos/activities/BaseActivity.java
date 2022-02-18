package com.example.bolsasalesianos.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.database.Database;
import com.example.bolsasalesianos.pojos.Credential;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends GenericActivity {
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initializeSharedPreferences();
        database = new Database();
        tryLogin();
    }

    /*
    * Realiza una busqueda del un usuario en la base de datos y en caso de que esta busqueda resulte
    * correcta redirigira al usuario a la pestaña correspondiente.
    * */
    public void tryLogin() {
        Call<Credential> call = database.getServices().searchCredential(new Credential(
                getLoginSP().getString("user", null), getLoginSP().getString("pass", null)));
        call.enqueue(new Callback<Credential>() {
            @Override
            public void onResponse(Call<Credential> call, Response<Credential> response) {
                if (response.body().getId() == null) {
                    startActivityAndFinishActually(getApplicationContext(), LogActivity.class);
                } else {
                    setLastConnection(response.body());
                    startActivityAndFinishActually(getApplicationContext(), MainActivity.class);
                }
            }
            @Override
            public void onFailure(Call<Credential> call, Throwable t) {

            }
        });
    }


}