package com.tareas.tareas.service;

import com.tareas.tareas.entity.Tarea;
import com.tareas.tareas.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    private final TareaRepository repo;

    public TareaService(TareaRepository repo) {
        this.repo = repo;
    }

    public Tarea crear(Tarea tarea) {

        if (tarea.getTitulo() == null || tarea.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }

        return repo.save(tarea);
    }

    public Tarea buscarPorId(Long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tarea no encontrada: " + id));
    }

    public Tarea completar(Long id) {

        Tarea t = buscarPorId(id);

        t.setCompletada(true);

        return repo.save(t);
    }
}