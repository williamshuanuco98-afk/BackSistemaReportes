package com.sistemareportes.backsistema.dto;

import java.util.List;

public class IncidentResponse {
    private String id; // Codigo folio, ej: "INC-101"
    private String category;
    private String location;
    private String urgency;
    private String status;
    private String title;
    private String description;
    private String author; // Correo del autor
    private String assignedTo; // Correo del técnico, o vacío
    private List<String> comments; // Lista de comentarios formateados

    public IncidentResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    public List<String> getComments() { return comments; }
    public void setComments(List<String> comments) { this.comments = comments; }
}
