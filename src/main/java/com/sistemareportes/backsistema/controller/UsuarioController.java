package com.sistemareportes.backsistema.controller;

import com.sistemareportes.backsistema.dto.LoginResponse;
import com.sistemareportes.backsistema.model.Rol;
import com.sistemareportes.backsistema.model.Usuario;
import com.sistemareportes.backsistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/tecnicos")
    public ResponseEntity<List<LoginResponse>> getTecnicos() {
        List<Usuario> list = usuarioService.findByRol(Rol.tecnico);
        List<LoginResponse> response = list.stream()
                .map(u -> new LoginResponse(u.getId(), u.getCorreo(), u.getRol().name(), u.getNombre()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
