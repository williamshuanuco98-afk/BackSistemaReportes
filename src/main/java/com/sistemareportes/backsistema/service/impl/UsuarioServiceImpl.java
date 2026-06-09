package com.sistemareportes.backsistema.service.impl;

import com.sistemareportes.backsistema.dto.RegisterRequest;
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

    @Override
    public Usuario registrar(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        // 1. Validar que termine con @utp.edu.pe
        if (!email.endsWith("@utp.edu.pe")) {
            throw new RuntimeException("El correo debe pertenecer al dominio institucional (@utp.edu.pe)");
        }

        // 2. Validar si ya existe
        if (usuarioRepository.findByCorreo(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya se encuentra registrado");
        }

        // 3. Extraer la parte local (antes del @)
        String localPart = email.substring(0, email.indexOf("@"));

        Rol userRole;
        if (localPart.matches("^u\\d+$")) {
            userRole = Rol.alumno;
        } else if (localPart.matches("^p\\d{4}$")) {
            userRole = Rol.profesor;
        } else if (localPart.matches("^t\\d{4}$")) {
            userRole = Rol.tecnico;
        } else if (localPart.matches("^a\\d{4}$")) {
            userRole = Rol.admin;
        } else {
            throw new RuntimeException("El formato del correo institucional no es válido para asignar un rol. " +
                    "Estudiantes: u[dígitos], Profesores: p[4 dígitos], Técnicos: t[4 dígitos], Admins: a[4 dígitos].");
        }

        Usuario usuario = new Usuario(
                request.getName(),
                request.getEmail(), // Guardar el email del request
                request.getPassword(),
                userRole
        );

        return usuarioRepository.save(usuario);
    }
}
