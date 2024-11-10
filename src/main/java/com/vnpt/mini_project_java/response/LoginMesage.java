package com.vnpt.mini_project_java.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class LoginMesage {

    @JsonProperty("message")
    private String message;
    
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    @JsonProperty("isUser")
    private boolean isUser;

    @JsonProperty("isUserVip")
    private boolean isUserVip;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("isCaptchaValid")
    private boolean isCaptchaValid;

    public LoginMesage(String message, Boolean status, boolean isAdmin,boolean isUser, boolean isUserVip, boolean isCaptchaValid) {
        this.message = message;
        this.status = status;
        this.isAdmin = isAdmin;
        this.isUser = isUser;
        this.isUserVip = isUserVip;
        this.isCaptchaValid = isCaptchaValid;
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