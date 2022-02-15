package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Selection {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("selected")
    @Expose
    private Integer selected;
    @SerializedName("vacant")
    @Expose
    private Integer vacant;

    /**
     * No args constructor for use in serialization
     *
     */
    public Selection() {
    }

    /**
     *
     * @param date
     * @param student
     * @param id
     * @param selected
     * @param vacant
     */
    public Selection(Integer id, String student, String date, Integer selected, Integer vacant) {
        super();
        this.id = id;
        this.student = student;
        this.date = date;
        this.selected = selected;
        this.vacant = vacant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getVacant() {
        return vacant;
    }

    public void setVacant(Integer vacant) {
        this.vacant = vacant;
    }

}