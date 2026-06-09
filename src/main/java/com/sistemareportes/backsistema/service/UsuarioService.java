package com.sistemareportes.backsistema.service;

import com.sistemareportes.backsistema.dto.RegisterRequest;
import com.sistemareportes.backsistema.model.Rol;
import com.sistemareportes.backsistema.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario findByCorreo(String correo);
    Usuario login(String correo, String contrasena);
    List<Usuario> findByRol(Rol rol);
    Usuario registrar(RegisterRequest request);
}
