package com.vnpt.mini_project_java.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginMesage {

    @JsonProperty("message")
    private String message;
    
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    
    @JsonProperty("status")
    private Boolean status;

    public LoginMesage(String message, Boolean status, boolean isAdmin) {
        this.message = message;
        this.status = status;
        this.isAdmin = isAdmin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public boolean isSuccess() {
        return status != null && status;
    }
}