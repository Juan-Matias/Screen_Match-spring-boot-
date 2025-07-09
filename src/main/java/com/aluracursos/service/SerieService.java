package com.aluracursos.service;


import com.aluracursos.dto.SerieDTO;
import com.aluracursos.repository.ISerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Anotacion para nuestro servicio Web
@Service
public class SerieService {
    @Autowired
    private ISerieRepository repository;

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
