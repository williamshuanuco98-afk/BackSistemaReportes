package com.sistemareportes.backsistema.controller;

import com.sistemareportes.backsistema.dto.GestionRequest;
import com.sistemareportes.backsistema.dto.IncidentRequest;
import com.sistemareportes.backsistema.dto.IncidentResponse;
import com.sistemareportes.backsistema.service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    @Autowired
    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getIncidencias(@RequestParam(required = false) String userEmail) {
        return ResponseEntity.ok(incidenciaService.getIncidencias(userEmail));
    }

    @PostMapping
    public ResponseEntity<?> createIncidencia(@Valid @RequestBody IncidentRequest request) {
        try {
            IncidentResponse response = incidenciaService.createIncidencia(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/urgencia")
    public ResponseEntity<?> escalateIncidencia(@PathVariable String id) {
        try {
            IncidentResponse response = incidenciaService.escalateIncidencia(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/asignar")
    public ResponseEntity<?> assignTechnician(@PathVariable String id, @RequestParam(required = false) String tecnicoEmail) {
        try {
            IncidentResponse response = incidenciaService.assignTechnician(id, tecnicoEmail);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/gestion")
    public ResponseEntity<?> gestionarIncidencia(@PathVariable String id, @RequestBody GestionRequest request) {
        try {
            IncidentResponse response = incidenciaService.gestionarIncidencia(
                    id,
                    request.getStatus(),
                    request.getComment(),
                    request.getUserEmail()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
