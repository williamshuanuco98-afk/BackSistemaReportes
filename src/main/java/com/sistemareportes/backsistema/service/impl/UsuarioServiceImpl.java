package com.sistemareportes.backsistema.service.impl;

import com.sistemareportes.backsistema.model.Rol;
import com.sistemareportes.backsistema.model.Usuario;
import com.sistemareportes.backsistema.repository.UsuarioRepository;
import com.sistemareportes.backsistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
    }

    @Override
    public Usuario login(String correo, String contrasena) {
        Usuario usuario = findByCorreo(correo);
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return usuario;
    }

    @Override
    public List<Usuario> findByRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }
}
