package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vacant {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("workstation")
    @Expose
    private String workstation;
    @SerializedName("vehicle")
    @Expose
    private Integer vehicle;
    @SerializedName("work_experience")
    @Expose
    private Integer workExperience;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("study")
    @Expose
    private String study;
    @SerializedName("working_day")
    @Expose
    private String workingDay;
    @SerializedName("hours")
    @Expose
    private Integer hours;

    /**
     * No args constructor for use in serialization
     *
     */
    public Vacant() {
    }

    /**
     *
     * @param workingDay
     * @param study
     * @param hours
     * @param workExperience
     * @param workstation
     * @param id
     * @param salary
     * @param vehicle
     */
    public Vacant(Integer id, String workstation, Integer vehicle, Integer workExperience, Integer salary, String study, String workingDay, Integer hours) {
        super();
        this.id = id;
        this.workstation = workstation;
        this.vehicle = vehicle;
        this.workExperience = workExperience;
        this.salary = salary;
        this.study = study;
        this.workingDay = workingDay;
        this.hours = hours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public Integer getVehicle() {
        return vehicle;
    }

    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

}
