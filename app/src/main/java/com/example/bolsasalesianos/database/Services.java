package com.example.bolsasalesianos.database;


import com.example.bolsasalesianos.pojos.Credential;
import com.example.bolsasalesianos.pojos.Enterprise;
import com.example.bolsasalesianos.pojos.Idiom;
import com.example.bolsasalesianos.pojos.IdiomStudent;
import com.example.bolsasalesianos.pojos.Status;
import com.example.bolsasalesianos.pojos.Student;
import com.example.bolsasalesianos.pojos.Study;
import com.example.bolsasalesianos.pojos.StudyStudent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Services {
    @POST("credentials?petition=one_object")
    Call<Credential> searchCredential(@Body Credential credential);

    @POST("credentials?petition=insert")
    Call<Status> newCredential(@Body Credential credential);

    @POST("students?petition=insert")
    Call<Status> newStudent(@Body Student student);

    @POST("students?petition=one_object")
    Call<Student> searchStudent(@Body Student student);

    @POST("enterprises?petition=one_object")
    Call<Enterprise> searchEnterprise(@Body Enterprise enterprise);

    @POST("enterprises?petition=insert")
    Call<Status> newEnterprise(@Body Enterprise enterprise);

    @PUT("credentials")
    Call<Status> updateCredential(@Body Credential credential);

    @PUT("students")
    Call<Status> updateStudent(@Body Student student);

    @POST("studies_student")
    Call<List<StudyStudent>> searchStudies(@Body StudyStudent studyStudent);

    @POST("idioms_student&option=idioms_by_student")
    Call<List<IdiomStudent>> searchIdiomsByStudent(@Body Student student);

    @POST("studies")
    Call<List<Study>> getStudies(@Body Study study);

    @POST("idioms")
    Call<List<Idiom>> getIdioms(@Body Idiom idiom);
}


