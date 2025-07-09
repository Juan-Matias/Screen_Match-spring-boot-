package com.aluracursos.controller;
import com.aluracursos.dto.SerieDTO;
import com.aluracursos.repository.ISerieRepository;
import com.aluracursos.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// modelo Rest: Leer la documentación
@RestController

public class SerieController  {
    @Autowired
    private SerieService servicio;

    // Mapeo de la pagina
    @GetMapping("/series")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }

}
