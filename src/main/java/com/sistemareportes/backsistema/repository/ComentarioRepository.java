package com.sistemareportes.backsistema.repository;

import com.sistemareportes.backsistema.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByIncidenciaIdOrderByFechaCreacionAsc(Integer incidenciaId);
}
