package com.example.denisprawira.ta.User.Model;

public class RetrofitResult {
    String code;
    String message;

    public RetrofitResult() {
    }

    public RetrofitResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
