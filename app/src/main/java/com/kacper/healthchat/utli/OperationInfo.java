package com.kacper.healthchat.utli;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Kacper on 18.11.2017.
 */

public class OperationInfo {
    private String message;
    private Boolean isSuccess;

    public OperationInfo(String message, Boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public OperationInfo() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
