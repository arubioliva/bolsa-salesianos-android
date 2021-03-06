package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Idiom {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("level")
    @Expose
    private String level;

    /**
     * No args constructor for use in serialization
     */
    public Idiom() {
    }

    /**
     * @param level
     * @param language
     * @param id
     */
    public Idiom(Integer id, String language, String level) {
        super();
        this.id = id;
        this.language = language;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

}