package com.example.bolsasalesianos.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Status {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private Error error;

    public Status() {
    }

    public Status(Integer status, Error error) {
        super();
        this.status = status;
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}

class Error {

    @SerializedName("errorInfo")
    @Expose
    private List<String> errorInfo = null;

    public Error() {
    }

    public Error(List<String> errorInfo) {
        super();
        this.errorInfo = errorInfo;
    }

    public List<String> getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(List<String> errorInfo) {
        this.errorInfo = errorInfo;
    }

}