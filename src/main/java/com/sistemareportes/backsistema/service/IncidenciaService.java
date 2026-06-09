package com.sistemareportes.backsistema.service;

import com.sistemareportes.backsistema.dto.IncidentRequest;
import com.sistemareportes.backsistema.dto.IncidentResponse;

import java.util.List;

public interface IncidenciaService {
    List<IncidentResponse> getIncidencias(String userEmail);
    IncidentResponse createIncidencia(IncidentRequest request);
    IncidentResponse escalateIncidencia(String codigoFolio);
    IncidentResponse assignTechnician(String codigoFolio, String tecnicoEmail);
    IncidentResponse gestionarIncidencia(String codigoFolio, String status, String comment, String userEmail);
}
