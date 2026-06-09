package com.sistemareportes.backsistema.config;

import com.sistemareportes.backsistema.model.Rol;
import com.sistemareportes.backsistema.model.Usuario;
import com.sistemareportes.backsistema.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario[] initialUsers = {
                new Usuario("Alumno UTP", "u12345678@utp.edu.pe", "u12345678", Rol.alumno),
                new Usuario("Profesor UTP", "c12345@utp.edu.pe", "c12345", Rol.profesor),
                new Usuario("Ing. Carlos Mendoza (Electricidad)", "t12345@utp.edu.pe", "t12345", Rol.tecnico),
                new Usuario("Ing. Ana Torres (Redes y TI)", "t12346@utp.edu.pe", "t12346", Rol.tecnico),
                new Usuario("Téc. Luis Delgado (Mantenimiento)", "t12347@utp.edu.pe", "t12347", Rol.tecnico),
                new Usuario("Administrador del Sistema", "admin@utp.edu.pe", "admin123", Rol.admin)
            };

            usuarioRepository.saveAll(Arrays.asList(initialUsers));
            System.out.println("Base de datos inicializada con usuarios por defecto.");
        }
    }
}
