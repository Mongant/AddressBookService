package com.mongant.model;

public class ApiError {

    private String nameFilter;
    private String message;

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "nameFilter='" + nameFilter + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
