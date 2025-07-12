package com.aluracursos.dto;

import com.aluracursos.model.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
          int totalDeTemporada,
          double evaluacion,
          String poster,
          Categoria genero,
          String actores,
          String sinopsis
) {
}
