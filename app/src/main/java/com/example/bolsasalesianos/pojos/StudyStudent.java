package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyStudent {

    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("study")
    @Expose
    private String study;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;

    /**
     * No args constructor for use in serialization
     */
    public StudyStudent() {
    }

    /**
     * @param study
     * @param student
     * @param start
     * @param end
     */
    public StudyStudent(String student, String study, String start, String end) {
        super();
        this.student = student;
        this.study = study;
        this.start = start;
        this.end = end;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}