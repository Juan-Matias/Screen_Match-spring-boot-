package com.aluracursos.controller;
import com.aluracursos.dto.SerieDTO;
import com.aluracursos.repository.ISerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// modelo Rest: Leer la documentación
@RestController

public class serieController  {
    //Atributos
    @Autowired
    private ISerieRepository repository;

    // Mapeo de la pagina
    @GetMapping("/series")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return repository.findAll().stream()
                .map(s -> new SerieDTO(
                        s.getTitulo(),
                        s.getTotalDeTemporada(),
                        s.getEvaluacion(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getActores(),
                        s.getSinopsis()
                )).collect(Collectors.toList());
    }
}
