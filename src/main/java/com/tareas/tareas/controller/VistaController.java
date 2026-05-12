package com.tareas.tareas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/tareas")
    public String mostrarTareas() {

        return "tareas";
    }
}