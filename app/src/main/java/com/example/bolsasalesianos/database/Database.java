package com.example.bolsasalesianos.database;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Database {

    private Services services;

    public Database() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://557ded9c.gclientes.com/api_rubio/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        services = retrofit.create(Services.class);
    }


    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

}
