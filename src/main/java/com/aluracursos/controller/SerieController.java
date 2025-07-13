package com.aluracursos.controller;

import com.aluracursos.dto.EpisodioDTO;
import com.aluracursos.dto.SerieDTO;
import com.aluracursos.model.Episodio;
import com.aluracursos.repository.ISerieRepository;
import com.aluracursos.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// modelo Rest: Leer la documentación
@RestController
@RequestMapping("/series")

public class SerieController {
    @Autowired
    private SerieService servicio;

    // Mapeo de la pagina
    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries() {
        return servicio.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5() {
        return servicio.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientoMasRecientes() {
        return servicio.obtenerLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodaLasTemporadas(@PathVariable Long id) {
        return servicio.obtenerTodasLasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporadas}")
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable Long id
            , @PathVariable Long numeroTemporadas)
    {
        return servicio.obtenerTemporadasPorNumero(id,numeroTemporadas);
    }

    @GetMapping ("categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorCategoria(@PathVariable String nombreGenero){
        return servicio.obtenerSeriesPorCategoria(nombreGenero);

    }
}
