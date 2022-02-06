package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credential {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("last_connection")
    @Expose
    private String lastConnection;

    public Credential() {
    }


    public Credential(String user) {
        super();
        this.user = user;
    }

    public Credential(String user, String pass) {
        super();
        this.user = user;
        this.pass = pass;
    }

    public Credential(String user, String pass, String lastConnection, String type) {
        super();
        this.user = user;
        this.pass = pass;
        this.lastConnection = lastConnection;
        this.type = type;
    }

    public Credential(String user, String pass, String type, String id, String lastConnection) {
        super();
        this.user = user;
        this.pass = pass;
        this.type = type;
        this.id = id;
        this.lastConnection = lastConnection;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

}
