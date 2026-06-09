package com.sistemareportes.backsistema.repository;

import com.sistemareportes.backsistema.model.Rol;
import com.sistemareportes.backsistema.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRol(Rol rol);
}
