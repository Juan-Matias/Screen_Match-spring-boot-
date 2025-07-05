package com.aluracursos.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias("Title") String Titulo,
        @JsonAlias("totalSeasons") String totalDeTemporada,
        @JsonAlias("imdbRating") double evaluacion,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Plot") String sinopsis,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Actors") String actores
) {
    public int totalTemporadas() {
        try {
            return Integer.parseInt(totalDeTemporada);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
