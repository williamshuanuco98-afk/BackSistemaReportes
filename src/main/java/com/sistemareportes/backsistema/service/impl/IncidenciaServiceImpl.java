package com.sistemareportes.backsistema.service.impl;

import com.sistemareportes.backsistema.dto.IncidentRequest;
import com.sistemareportes.backsistema.dto.IncidentResponse;
import com.sistemareportes.backsistema.model.*;
import com.sistemareportes.backsistema.repository.ComentarioRepository;
import com.sistemareportes.backsistema.repository.IncidenciaRepository;
import com.sistemareportes.backsistema.repository.UsuarioRepository;
import com.sistemareportes.backsistema.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioRepository comentarioRepository;
    private final Random random = new Random();

    @Autowired
    public IncidenciaServiceImpl(IncidenciaRepository incidenciaRepository,
                                  UsuarioRepository usuarioRepository,
                                  ComentarioRepository comentarioRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidentResponse> getIncidencias(String userEmail) {
        List<Incidencia> list;
        if (userEmail != null && !userEmail.trim().isEmpty()) {
            Usuario usuario = usuarioRepository.findByCorreo(userEmail).orElse(null);
            if (usuario != null) {
                if (usuario.getRol() == Rol.alumno) {
                    list = incidenciaRepository.findByAutorCorreoOrderByFechaCreacionDesc(userEmail);
                } else if (usuario.getRol() == Rol.tecnico) {
                    list = incidenciaRepository.findByTecnicoCorreoOrderByFechaCreacionDesc(userEmail);
                } else {
                    list = incidenciaRepository.findAllByOrderByFechaCreacionDesc();
                }
            } else {
                list = incidenciaRepository.findAllByOrderByFechaCreacionDesc();
            }
        } else {
            list = incidenciaRepository.findAllByOrderByFechaCreacionDesc();
        }
        return list.stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional
    public IncidentResponse createIncidencia(IncidentRequest request) {
        Usuario autor = usuarioRepository.findByCorreo(request.getAuthorEmail())
                .orElseThrow(() -> new RuntimeException("Usuario autor no encontrado: " + request.getAuthorEmail()));

        // Generar folio único
        String folio;
        do {
            folio = "INC-" + (random.nextInt(9000) + 1000);
        } while (incidenciaRepository.findByCodigoFolio(folio).isPresent());

        Incidencia inc = new Incidencia();
        inc.setCodigoFolio(folio);
        inc.setTitulo(request.getTitle());
        inc.setDescripcion(request.getDescription());
        inc.setUbicacion(request.getLocation());
        inc.setEstado(Estado.pendiente);

        // Mapear categoría segura
        try {
            inc.setCategoria(Categoria.valueOf(request.getCategory().toLowerCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Categoría inválida: " + request.getCategory());
        }

        // Mapear urgencia segura
        try {
            inc.setUrgency(Urgencia.valueOf(request.getUrgency().toLowerCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Urgencia inválida: " + request.getUrgency());
        }

        inc.setAutor(autor);
        inc = incidenciaRepository.save(inc);

        return mapToResponse(inc);
    }

    @Override
    @Transactional
    public IncidentResponse escalateIncidencia(String codigoFolio) {
        Incidencia inc = incidenciaRepository.findByCodigoFolio(codigoFolio)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada: " + codigoFolio));

        inc.setUrgency(Urgencia.alta);
        inc = incidenciaRepository.save(inc);
        return mapToResponse(inc);
    }

    @Override
    @Transactional
    public IncidentResponse assignTechnician(String codigoFolio, String tecnicoEmail) {
        Incidencia inc = incidenciaRepository.findByCodigoFolio(codigoFolio)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada: " + codigoFolio));

        if (tecnicoEmail == null || tecnicoEmail.trim().isEmpty()) {
            inc.setTecnico(null);
        } else {
            Usuario tech = usuarioRepository.findByCorreo(tecnicoEmail)
                    .orElseThrow(() -> new RuntimeException("Técnico no encontrado: " + tecnicoEmail));
            if (tech.getRol() != Rol.tecnico) {
                throw new RuntimeException("El usuario asignado debe tener el rol de técnico");
            }
            inc.setTecnico(tech);
        }

        inc = incidenciaRepository.save(inc);
        return mapToResponse(inc);
    }

    @Override
    @Transactional
    public IncidentResponse gestionarIncidencia(String codigoFolio, String status, String comment, String userEmail) {
        Incidencia inc = incidenciaRepository.findByCodigoFolio(codigoFolio)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada: " + codigoFolio));

        if (status != null && !status.trim().isEmpty()) {
            try {
                inc.setEstado(Estado.valueOf(status.toLowerCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado inválido: " + status);
            }
        }

        if (comment != null && !comment.trim().isEmpty() && userEmail != null && !userEmail.trim().isEmpty()) {
            Usuario user = usuarioRepository.findByCorreo(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuario de gestión no encontrado: " + userEmail));

            Comentario nuevoComentario = new Comentario(inc, user, comment);
            comentarioRepository.save(nuevoComentario);
            inc.getComentarios().add(nuevoComentario);
        }

        inc = incidenciaRepository.save(inc);
        return mapToResponse(inc);
    }

    private IncidentResponse mapToResponse(Incidencia inc) {
        IncidentResponse res = new IncidentResponse();
        res.setId(inc.getCodigoFolio());
        res.setLocation(inc.getUbicacion());
        res.setUrgency(inc.getUrgency().name());
        res.setStatus(inc.getEstado().name());
        res.setTitle(inc.getTitulo());
        res.setDescription(inc.getDescripcion());
        res.setAuthor(inc.getAutor().getCorreo());
        res.setAssignedTo(inc.getTecnico() != null ? inc.getTecnico().getCorreo() : "");

        // Traducir categoría para el frontend
        switch (inc.getCategoria()) {
            case infra:
                res.setCategory("Infraestructura");
                break;
            case tech:
                res.setCategory("Tecnología");
                break;
            case security:
                res.setCategory("Seguridad");
                break;
            case clean:
                res.setCategory("Limpieza");
                break;
        }

        // Mapear comentarios en formato de texto plano con prefijos esperados por el frontend
        List<String> commentStrings = inc.getComentarios().stream().map(c -> {
            Usuario user = c.getUsuario();
            String prefix = "";
            if (user.getRol() == Rol.admin) {
                prefix = "[Administración] ";
            } else if (user.getRol() == Rol.tecnico) {
                prefix = "[Técnico] ";
            }
            return prefix + c.getMensaje();
        }).toList();
        res.setComments(commentStrings);

        return res;
    }
}
