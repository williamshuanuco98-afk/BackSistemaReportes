package com.sistemareportes.backsistema.dto;

import jakarta.validation.constraints.NotBlank;

public class IncidentRequest {

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @NotBlank(message = "La ubicación es obligatoria")
    private String location;

    @NotBlank(message = "La urgencia es obligatoria")
    private String urgency;

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "El correo del autor es obligatorio")
    private String authorEmail;

    public IncidentRequest() {}

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
}
