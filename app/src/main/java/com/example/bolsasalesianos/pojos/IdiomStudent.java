package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdiomStudent {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("idiom")
    @Expose
    private String idiom;

    public IdiomStudent() {
    }

    public IdiomStudent(String id, String language, String level, String student, String idiom) {
        super();
        this.id = id;
        this.language = language;
        this.level = level;
        this.student = student;
        this.idiom = idiom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getIdiom() {
        return idiom;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }

}