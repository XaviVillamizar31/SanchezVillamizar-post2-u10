package com.tareas.tareas.service;

import com.tareas.tareas.entity.Tarea;
import com.tareas.tareas.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    TareaRepository repo;

    @InjectMocks
    TareaService service;

    @Test
    void crear_conTituloValido_guardaYRetorna() {

        Tarea t = new Tarea();
        t.setTitulo("Estudiar JUnit");

        when(repo.save(any())).thenReturn(t);

        assertThat(service.crear(t).getTitulo())
                .isEqualTo("Estudiar JUnit");

        verify(repo).save(t);
    }

    @Test
    void crear_conTituloVacio_lanzaIllegalArgumentException() {

        Tarea t = new Tarea();
        t.setTitulo(" ");

        assertThrows(IllegalArgumentException.class,
                () -> service.crear(t));

        verify(repo, never()).save(any());
    }

    @Test
    void buscarPorId_noExiste_lanzaEntityNotFoundException() {

        when(repo.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void completar_tareaExistente_marcaComoCompletada() {

        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Prueba");
        t.setCompletada(false);

        when(repo.findById(1L)).thenReturn(Optional.of(t));
        when(repo.save(any())).thenReturn(t);

        Tarea resultado = service.completar(1L);

        assertThat(resultado.isCompletada()).isTrue();

        verify(repo).save(t);
    }
}