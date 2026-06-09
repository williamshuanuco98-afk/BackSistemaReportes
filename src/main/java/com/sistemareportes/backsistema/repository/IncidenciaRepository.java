package com.sistemareportes.backsistema.repository;

import com.sistemareportes.backsistema.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {
    Optional<Incidencia> findByCodigoFolio(String codigoFolio);
    List<Incidencia> findByAutorCorreoOrderByFechaCreacionDesc(String correo);
    List<Incidencia> findByTecnicoCorreoOrderByFechaCreacionDesc(String correo);
    List<Incidencia> findAllByOrderByFechaCreacionDesc();
}
