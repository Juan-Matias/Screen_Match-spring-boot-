package com.aluracursos.service;


import com.aluracursos.dto.SerieDTO;
import com.aluracursos.model.Serie;
import com.aluracursos.repository.ISerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Anotacion para nuestro servicio Web
@Service
public class SerieService {
    @Autowired
    private ISerieRepository repository;

    // Metodo obtener todas la series
    public List<SerieDTO> obtenerTodasLasSeries(){
        return convierteDatos(repository.findAll());
    }

    //Metodo de obtener top 5 de series
    public List<SerieDTO> obtenerTop5() {
    return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    //Metodo
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    //convierte datos
    public List<SerieDTO> convierteDatos(List <Serie> serie){
        return serie.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalDeTemporada(),
                        s.getEvaluacion(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getActores(),
                        s.getSinopsis()
                )).collect(Collectors.toList());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalDeTemporada(),
                    s.getEvaluacion(),
                    s.getPoster(),
                    s.getGenero(),
                    s.getActores(),
                    s.getSinopsis());
        }
        return null;
        }
    }

