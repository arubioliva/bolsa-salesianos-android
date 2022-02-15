
        package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("dni")
    @Expose
    private String dni;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("employed")
    @Expose
    private String employed;
    @SerializedName("data_transf")
    @Expose
    private String dataTransf;
    @SerializedName("resume")
    @Expose
    private String resume;
    @SerializedName("credential")
    @Expose
    private String credential;

    public Student() {
    }

    public Student(String dni, String name, String lastName, String phone, String email, String license, String employed,String dataTransf) {
        super();
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.license = license;
        this.employed = employed;
        this.dataTransf = dataTransf;
    }
    public Student(String dni, String name, String lastName, String phone, String email,String resume, String license, String employed,String dataTransf) {
        super();
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.resume = resume;
        this.license = license;
        this.employed = employed;
        this.dataTransf = dataTransf;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getEmployed() {
        return employed;
    }

    public void setEmployed(String employed) {
        this.employed = employed;
    }

    public String getDataTransf() {
        return dataTransf;
    }

    public void setDataTransf(String dataTransf) {
        this.dataTransf = dataTransf;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

}
