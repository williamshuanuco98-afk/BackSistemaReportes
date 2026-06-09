package com.sistemareportes.backsistema.controller;

import com.sistemareportes.backsistema.dto.LoginRequest;
import com.sistemareportes.backsistema.dto.LoginResponse;
import com.sistemareportes.backsistema.model.Usuario;
import com.sistemareportes.backsistema.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.login(request.getEmail(), request.getPassword());
            LoginResponse response = new LoginResponse(
                    usuario.getId(),
                    usuario.getCorreo(),
                    usuario.getRol().name(),
                    usuario.getNombre()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
