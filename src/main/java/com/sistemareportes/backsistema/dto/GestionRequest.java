package com.sistemareportes.backsistema.dto;

public class GestionRequest {
    private String status;
    private String comment;
    private String userEmail;

    public GestionRequest() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
